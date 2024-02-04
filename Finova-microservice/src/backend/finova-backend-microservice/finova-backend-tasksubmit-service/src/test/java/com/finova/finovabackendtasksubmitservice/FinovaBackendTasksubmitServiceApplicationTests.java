package com.finova.finovabackendtasksubmitservice;

import cn.hutool.jwt.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finova.finovabackendcommon.msg.auth.TokenForbiddenResponse;
import com.finova.finovabackendcommon.utils.jwt.IJWTInfo;
import com.finova.finovabackendcommon.utils.jwt.JWTInfo;
import com.finova.finovabackendmodel.domain.model.Task;
import com.finova.finovabackendtasksubmitservice.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class FinovaBackendTasksubmitServiceApplicationTests {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void contextLoads() throws JsonProcessingException {
        IJWTInfo user = new JWTInfo("lyslys", "3", "lyslys", "tokenId");
        JWTInfo finalUser = (JWTInfo) user;

        // 将用户信息序列化存入请求头
        ObjectMapper objectMapper = new ObjectMapper();
        String finalUserStr = null;
        try {
            finalUserStr = objectMapper.writeValueAsString(finalUser);
        } catch (JsonProcessingException e) {
            System.out.println("error");
        }

        System.out.println(finalUserStr);

        JWTInfo jwtInfo = objectMapper.readerFor(JWTInfo.class).readValue(finalUserStr);

        System.out.println(jwtInfo);
    }

    @Test
    public void testSelect() {
        Task task = taskMapper.selectByTaskId(9);
        System.out.println(task);
    }

    @Test
    public void testUpdate() {
        Task task = taskMapper.selectByTaskId(9);
        System.out.println(task);
        task.setStatus(0);
        taskMapper.update(task);
        System.out.println(taskMapper.selectByTaskId(9));
    }
}
