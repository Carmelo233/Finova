package com.finova.finovabackendfileservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.finova.finovabackendfileservice.mapper")
@EnableFeignClients(basePackages = {"com.finova.finovabackendserviceclient.service"})
@ComponentScan("com.finova")
public class FinovaBackendFileserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinovaBackendFileserviceApplication.class, args);
    }

}
