package com.sid.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class JPAMultiDatabaseServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(JPAMultiDatabaseServiceApp.class, args);
	}

}