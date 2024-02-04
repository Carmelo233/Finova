package com.finova.finovabackendtasksubmitservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.finova.finovabackendtasksubmitservice.mapper")
@ComponentScan("com.finova")
@EnableDiscoveryClient
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableFeignClients(basePackages = {"com.finova.finovabackendserviceclient.service"})
public class FinovaBackendTasksubmitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinovaBackendTasksubmitServiceApplication.class, args);
    }

}
