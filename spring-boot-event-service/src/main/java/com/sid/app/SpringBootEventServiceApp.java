package com.sid.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootEventServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEventServiceApp.class, args);
    }

}