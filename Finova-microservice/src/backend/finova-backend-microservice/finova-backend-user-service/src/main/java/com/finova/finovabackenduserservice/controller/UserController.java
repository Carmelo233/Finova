package com.finova.finovabackenduserservice.controller;

import com.finova.finovabackendcommon.common.BaseResponse;
import com.finova.finovabackendcommon.common.ErrorCode;
import com.finova.finovabackendcommon.common.ResultUtils;
import com.finova.finovabackendcommon.exception.BusinessException;
import com.finova.finovabackendmodel.domain.model.User;
import com.finova.finovabackendmodel.result.response.ResultJSON;
import com.finova.finovabackenduserservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse<Integer> login(@RequestBody User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer uid = userService.handleLogin(username, password);
        return ResultUtils.success(uid);
    }

    @PostMapping("/register")
    public BaseResponse<Integer> register(@RequestBody User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int uid = userService.handleRegister(username, password);
        return ResultUtils.success(uid);
    }

    // todo 修改
    @PostMapping("/sms/send")
    public ResultJSON sendSms(@RequestBody Map<String, Object> requestMap, HttpServletRequest request) {
        return userService.sendSms(requestMap, request);
    }

    // todo 修改
    @PostMapping("/sms/login")
    public ResultJSON loginBySms(@RequestBody Map<String, Object> requestMap) {
        return userService.loginBySms(requestMap);

    }

    // todo 修改
    @PostMapping("/sms/bind")
    public ResultJSON bindPhoneNumber(@RequestBody Map<String, Object> requestMap) {
        return userService.handleBind(requestMap);
    }
}
