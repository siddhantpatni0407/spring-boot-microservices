package com.sid.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApp.class, args);
    }

    /*
    // To remove the Banner
    public static void main(String[] args) {
        new SpringApplicationBuilder(EmailServiceApp.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
    */

}