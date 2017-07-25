package com.wormwood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by Donnie on 2017/2/17.
 */
@SpringBootApplication
@EnableFeignClients
public class GMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(GMSApplication.class);
    }
}

