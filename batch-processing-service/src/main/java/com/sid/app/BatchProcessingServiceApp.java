package com.sid.app;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchProcessingServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(BatchProcessingServiceApp.class, args);
    }
}