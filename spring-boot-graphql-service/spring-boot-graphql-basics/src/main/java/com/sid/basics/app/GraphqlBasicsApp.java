package com.sid.basics.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages = "com.sid.basics.app.${sec}")
public class GraphqlBasicsApp {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlBasicsApp.class, args);
    }

}