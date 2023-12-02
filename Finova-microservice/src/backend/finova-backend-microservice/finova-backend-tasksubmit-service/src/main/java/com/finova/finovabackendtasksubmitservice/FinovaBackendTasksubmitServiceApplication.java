package com.finova.finovabackendtasksubmitservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.finova.finovabackendtasksubmitservice.mapper")
@ComponentScan("com.finova")
public class FinovaBackendTasksubmitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinovaBackendTasksubmitServiceApplication.class, args);
    }

}
