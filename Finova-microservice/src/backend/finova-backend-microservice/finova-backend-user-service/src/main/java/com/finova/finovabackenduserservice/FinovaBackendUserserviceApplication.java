package com.finova.finovabackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.finova.finovabackenduserservice.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.finova")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.finova.finovabackendserviceclient.service"})
public class FinovaBackendUserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinovaBackendUserserviceApplication.class, args);
    }

}
