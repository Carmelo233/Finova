package com.finova.finovabackendcommon.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

/**
 * 短信下发工具类
 */
public class SmsTool {
    // 产品名称：阿里云云通信
    private static String product = "Dysmsapi";
    // 产品域名
    private static String domain = "dysmsapi.aliyuncs.com";
    // 开发者AK
    private static String accessKeyId = "LTAI5tJMP71xv3KtwuTc9VMn";
    private static String accessKeySecret = "YyAUrc8KWhvFeThnN9TkBifuSrXCjs";

    public static SendSmsResponse sendSms(String phoneNumber, String code) throws ClientException {

        // 官方SDK 1.0
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        /** use STS Token
         DefaultProfile profile = DefaultProfile.getProfile(
         "<your-region-id>",           // The region ID
         "<your-access-key-id>",       // The AccessKey ID of the RAM account
         "<your-access-key-secret>",   // The AccessKey Secret of the RAM account
         "<your-sts-token>");          // STS Token
         **/

        IAcsClient client = new DefaultAcsClient(profile);


        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("Finova");
        request.setTemplateCode("SMS_275430228");
        request.setPhoneNumbers(phoneNumber);
        request.setTemplateParam(code);

        SendSmsResponse response = client.getAcsResponse(request);
        System.out.println(new Gson().toJson(response));
        return response;


        // 旧版
        // 设置超时时间
//        System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); // 连接
//        System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读
//
//        // 初始化acsClient
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
//        DefaultProfile.addEndpoint("cn-hangzhou", product, domain);
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//
//        // 组装请求对象-具体描述见控制台-文档部分内容
//        SendSmsRequest request = new SendSmsRequest();
//        // 待发送手机号
//        request.setPhoneNumbers(phoneNumber);
//        // 短信签名-可在短信控制台中找到
//        request.setSignName("Finova");
//        // 短信模板-可在短信控制台中找到
//        request.setTemplateCode("SMS_275275257");
//        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
////        request.setTemplateParam(code);
//
//        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
//        //request.setSmsUpExtendCode("90997");
//
//        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        //request.setOutId("yourOutId");
//
//        //hint 此处可能会抛出异常，注意catch
//        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//        return sendSmsResponse;
    }
}
