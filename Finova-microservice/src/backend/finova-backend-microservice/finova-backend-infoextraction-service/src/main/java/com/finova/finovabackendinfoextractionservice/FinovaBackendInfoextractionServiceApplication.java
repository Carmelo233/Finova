package com.finova.finovabackendinfoextractionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@ComponentScan("com.finova")
@EnableDiscoveryClient
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableFeignClients(basePackages = {"com.finova.finovabackendserviceclient.service"})
public class FinovaBackendInfoextractionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinovaBackendInfoextractionServiceApplication.class, args);
    }

}
