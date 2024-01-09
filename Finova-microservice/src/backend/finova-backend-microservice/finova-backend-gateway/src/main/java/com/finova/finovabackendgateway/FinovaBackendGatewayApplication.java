package com.finova.finovabackendgateway;

import com.finova.finovabackendauthsdk.auth.client.EnableAceAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableAceAuthClient
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients({"com.finova.finovabackendauthsdk.auth.client.feign"})
public class FinovaBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinovaBackendGatewayApplication.class, args);
    }

}
