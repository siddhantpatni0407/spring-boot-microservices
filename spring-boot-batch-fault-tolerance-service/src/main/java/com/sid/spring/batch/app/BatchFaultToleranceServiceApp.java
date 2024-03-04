package com.sid.spring.batch.app;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchFaultToleranceServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(BatchFaultToleranceServiceApp.class, args);
    }

}
