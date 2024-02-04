package com.finova.finovabackenduserservice.service;

import com.finova.finovabackendmodel.domain.model.User;
import com.finova.finovabackendmodel.result.login.LoginResponse;
import com.finova.finovabackendmodel.result.response.ResultJSON;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public interface UserService {

    LoginResponse handleLogin(String username, String password);

    int handleRegister(String username, String password);

    ResultJSON handleSms(String phoneNumber);

    ResultJSON handleBind(Map<String, Object> requestMap);

    ResultJSON sendSms(Map<String, Object> requestMap, HttpServletRequest request);

    ResultJSON loginBySms(Map<String, Object> requestMap);
}
