package com.finova.finovabackenduserservice.service.impl;


import com.alibaba.fastjson.JSON;
import com.finova.finovabackendcommon.constant.RedisKeyConstant;
import com.finova.finovabackendcommon.utils.*;
import com.finova.finovabackendmodel.auth.OnlineLog;
import com.finova.finovabackendmodel.result.login.JWTGenResponse;
import com.finova.finovabackendmodel.result.login.LoginResponse;
import eu.bitwalker.useragentutils.UserAgent;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.finova.finovabackendcommon.common.ErrorCode;
import com.finova.finovabackendcommon.exception.BusinessException;
import com.finova.finovabackendcommon.utils.jwt.JWTInfo;
import com.finova.finovabackendmodel.domain.model.User;
import com.finova.finovabackendmodel.result.response.Code;
import com.finova.finovabackendmodel.result.response.ResultJSON;
import com.finova.finovabackendmodel.result.response.ResultMsg;
import com.finova.finovabackendserviceclient.service.AuthFeignClient;
import com.finova.finovabackenduserservice.dao.UserDao;
import com.finova.finovabackenduserservice.service.RedisService;
import com.finova.finovabackenduserservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String SALT = "Finova";
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AuthFeignClient authFeignClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.expire}")
    private int expire;

    private String tokenId = "TOKEN-USER-";


    @Override
    public LoginResponse handleLogin(String username, String password) {
        // 1. 校验参数
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        // 3. 检查用户是否存在
        User user = userDao.queryByUsernameAndEcryptPassword(username, encryptPassword);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }

        // 4. 生成 jwt
        JWTGenResponse data = authFeignClient.generateJWT(user);
        if (data == null || data.getJwtInfo() == null || StringUtils.isAnyBlank(data.getJwt(), data.getJwtInfo().getTokenId())) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "生成token失败");
        }

        // 5. 记录用户在线日志
        JWTInfo jwtInfo = data.getJwtInfo();

        final UserAgent userAgent = UserAgent.parseUserAgentString(WebUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getRemoteIP(WebUtils.getRequest());
        String address = AddressUtils.getRealAddressByIP(ip);

        OnlineLog onlineLog = new OnlineLog();
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 设置在线日志内容
        onlineLog.setBrowser(browser);
        onlineLog.setIpaddr(ip);
        onlineLog.setTokenId(jwtInfo.getTokenId());
        onlineLog.setLoginTime(System.currentTimeMillis());
        onlineLog.setUserId(jwtInfo.getUserId());
        onlineLog.setUserName(jwtInfo.getName());
        onlineLog.setLoginLocation(address);
        onlineLog.setOs(os);
        // 将 JWT 信息缓存在 Redis 并设置过期时间 todo 过期时间修改
        stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + jwtInfo.getTokenId(), JSON.toJSONString(onlineLog, false), expire * 100L, TimeUnit.MINUTES);
        stringRedisTemplate.opsForZSet().add((RedisKeyConstant.REDIS_KEY_TOKEN), jwtInfo.getTokenId(), 0);

        // 6. 将 jwt 返回
        String jwt = data.getJwt();

        return new LoginResponse(user.getUid(), jwt);
    }

    @Override
    public int handleRegister(String username, String password) {
        // 1. 非空校验
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 2. 上锁防止同一用户名并发注册
        synchronized (username.intern()) {
            // 账户不能重复
            long count = userDao.selectCountByUsername(username);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUsername(username);
            user.setPassword(encryptPassword);
            Integer i = userDao.insertUser(user);
            if (i == 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getUid();
        }
    }

    @Override
    public ResultJSON handleSms(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            log.error("参数丢失");
            return new ResultJSON(Code.BAD_REQUEST, false, "手机号为空");
        }

        User temp = userDao.queryByPhoneNumber(phoneNumber);
        if (temp == null) { // 若用户不存在，则注册，初始密码为1234567890
            log.info("注册成功，用户名、密码均为手机号" + phoneNumber);
//            return handleRegister(new User(phoneNumber, phoneNumber, phoneNumber));
            return null;
        } else { // 若用户已用手机号注册过，则直接登录
            log.info("登录成功" + temp.getUid());
            return new ResultJSON(Code.OK, temp.getUid(), ResultMsg.LOGIN_SUCCESS.getMsg());
        }
    }

    @Override
    public ResultJSON handleBind(Map<String, Object> requestMap) {
        String username = requestMap.get("username").toString(); // 获取当前操作用户的用户名
        String phoneNumber = requestMap.get("phoneNumber").toString(); // 获取用户输入的手机号
        String verifyCode = requestMap.get("verifyCode").toString(); // 获取用户输入的验证码

        // 首先确认验证码是否失效
        String redisAuthCode = redisService.get(tokenId + phoneNumber); // 获取redis中的value
        if (StringUtils.isEmpty(redisAuthCode)) {
            // 未取到验证码则已过期
            log.error("验证码已过期");
            return new ResultJSON(Code.BAD_REQUEST, false, "验证码已过期，绑定失败");
        } else if (!"".equals(redisAuthCode) && !verifyCode.equals(redisAuthCode)) {
            // redis中存的验证码不为空且验证码不正确
            log.error("验证码错误");
            return new ResultJSON(Code.BAD_REQUEST, false, "验证码错误，请重新输入");
        } else {
            log.info("验证码正确，处理绑定");
        }

        if (StringUtils.isBlank(phoneNumber)) {
            log.error("参数丢失");
            return new ResultJSON(Code.BAD_REQUEST, false, "手机号为空");
        }

        User temp = userDao.queryByPhoneNumber(phoneNumber);
        if (temp == null) {
            // 该手机号没被绑定过
            log.info("手机号" + phoneNumber + "未被绑定");
            if (userDao.updatePhoneNumber(username, phoneNumber) > 0) {
                log.info("绑定成功");
                return new ResultJSON(Code.OK, true, "绑定成功");
            } else {
                log.error("绑定失败");
                return new ResultJSON(Code.GONE, false, "绑定失败");
            }
        } else {
            // 手机号被绑定了
            if (temp.getUsername().equals(username)) {
                log.error("该用户已绑定此手机号");
                return new ResultJSON(Code.BAD_REQUEST, false, "不能重复绑定");
            } else {
                log.error("该手机号已被别的用户绑定");
                return new ResultJSON(Code.BAD_REQUEST, false, "该手机号已被绑定");
            }
        }
    }

    @Override
    public ResultJSON sendSms(Map<String, Object> requestMap, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String phoneNumber = requestMap.get("phoneNumber").toString();
        // 调用工具栏中生成验证码的方法
        String code = GenerateRandomCode.generateVerifyCode(6);
        // 填充验证码
        String templateParm = "{\"code\":\"" + code + "\"}";
        // 传入手机号码及短信模板中的验证码占位符
        SendSmsResponse response = null;
        try {
            response = SmsTool.sendSms(phoneNumber, templateParm);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }

        // 存入map中返回给前端
        map.put("verifyCode", code);
        map.put("phoneNumber", phoneNumber);
        request.getSession().setAttribute("CodePhone", map);
        if (response.getCode().equals("OK")) {
            log.info("验证码发送成功");
            map.put("isOK", "OK");
            // 验证码绑定手机号并存储到redis
            redisService.set(tokenId + phoneNumber, code);
            // 设置过期时间为5分钟
            redisService.expire(tokenId + phoneNumber, 300);

            return new ResultJSON(Code.OK, true, "验证码发送成功");
        } else {
            log.error("验证码发送失败");
            map.put("isOK", "NOT_OK");

            return new ResultJSON(Code.BAD_REQUEST, false, "验证码发送失败");
        }
    }

    @Override
    public ResultJSON loginBySms(Map<String, Object> requestMap) {
        String phoneNumber = requestMap.get("phoneNumber").toString(); // 获取用户输入的手机号
        String verifyCode = requestMap.get("verifyCode").toString(); // 获取用户输入的验证码

        // 首先确认验证码是否失效
        String redisAuthCode = redisService.get(tokenId + phoneNumber); // 获取redis中的value
        if (StringUtils.isEmpty(redisAuthCode)) {
            // 未取到验证码则已过期
            log.error("验证码已过期");
            return new ResultJSON(Code.BAD_REQUEST, false, "验证码已过期，登录失败");
        } else if (!"".equals(redisAuthCode) && !verifyCode.equals(redisAuthCode)) {
            // redis中存的验证码不为空且验证码不正确
            log.error("验证码错误");
            return new ResultJSON(Code.BAD_REQUEST, false, "验证码错误，请重新输入");
        } else {
            log.info("验证码正确，处理登录");
            return handleSms(phoneNumber);
        }
    }
}
