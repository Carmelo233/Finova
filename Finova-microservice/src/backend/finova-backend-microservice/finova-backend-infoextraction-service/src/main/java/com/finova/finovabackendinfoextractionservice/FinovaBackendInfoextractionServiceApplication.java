package com.finova.finovabackendinfoextractionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.finova.finovabackendserviceclient.service"})
public class FinovaBackendInfoextractionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinovaBackendInfoextractionServiceApplication.class, args);
    }

}
