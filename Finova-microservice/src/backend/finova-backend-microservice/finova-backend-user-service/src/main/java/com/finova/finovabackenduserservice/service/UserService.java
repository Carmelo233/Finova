package com.finova.finovabackenduserservice.service;

import com.finova.finovabackendmodel.domain.User;
import com.finova.finovabackendmodel.result.ResultJSON;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public interface UserService {

    ResultJSON handleLogin(User user);

    ResultJSON handleRegister(User user);

    ResultJSON handleSms(String phoneNumber);

    ResultJSON handleBind(Map<String, Object> requestMap);

    ResultJSON sendSms(Map<String, Object> requestMap, HttpServletRequest request);

    ResultJSON loginBySms(Map<String, Object> requestMap);
}
