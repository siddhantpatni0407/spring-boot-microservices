package com.sid.app.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type App properties.
 *
 * @author Siddhant Patni
 */
@Data
@Component
public class AppProperties {

    @Value("${spring.datasource.mysql.url}")
    private String mySQLURL;

    @Value("${spring.datasource.mysql.driver-class-name}")
    private String mySQLDriverClassName;

    @Value("${spring.datasource.mysql.username}")
    private String mySQLUsername;

    @Value("${spring.datasource.mysql.password}")
    private String mySQLPassword;

    @Value("${spring.datasource.postgresql.url}")
    private String postgresqlURL;

    @Value("${spring.datasource.postgresql.driver-class-name}")
    private String postgresqlDriverClassName;

    @Value("${spring.datasource.postgresql.username}")
    private String postgresqlUsername;

    @Value("${spring.datasource.postgresql.password}")
    private String postgresqlPassword;

}