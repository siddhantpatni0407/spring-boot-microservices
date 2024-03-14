package com.sid.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to bootstrap the File Storage Service application.
 */
@SpringBootApplication
public class FileStorageServiceApp {

    /**
     * Main method to start the File Storage Service application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(FileStorageServiceApp.class, args);
    }

}