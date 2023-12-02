package com.finova.finovabackenduserservice.controller;

import com.finova.finovabackendmodel.domain.model.User;
import com.finova.finovabackendmodel.result.ResultJSON;
import com.finova.finovabackenduserservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultJSON login(@RequestBody User user) {
        return userService.handleLogin(user);
    }

    @PostMapping("/register")
    public ResultJSON register(@RequestBody User user) {
        return userService.handleRegister(user);
    }

    @PostMapping("/sms/send")
    public ResultJSON sendSms(@RequestBody Map<String, Object> requestMap, HttpServletRequest request) {
        return userService.sendSms(requestMap, request);
    }

    @PostMapping("/sms/login")
    public ResultJSON loginBySms(@RequestBody Map<String, Object> requestMap) {
        return userService.loginBySms(requestMap);

    }

    @PostMapping("/sms/bind")
    public ResultJSON bindPhoneNumber(@RequestBody Map<String, Object> requestMap) {
        return userService.handleBind(requestMap);
    }
}
