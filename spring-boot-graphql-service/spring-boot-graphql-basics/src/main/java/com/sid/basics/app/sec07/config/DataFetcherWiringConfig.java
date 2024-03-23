package com.sid.basics.app.sec07.config;

import com.sid.basics.app.sec07.service.CustomerOrderDataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * @author Siddhant Patni
 */
@Configuration
public class DataFetcherWiringConfig {

    @Autowired
    private CustomerOrderDataFetcher dataFetcher;

    @Bean
    public RuntimeWiringConfigurer configurer() {
        return c -> c.type("Query", builder -> builder.dataFetcher("customers", dataFetcher));
    }

   /* @Bean
    public RuntimeWiringConfigurer configurer() {
        return c -> c.type("Query", builder -> builder.dataFetchers(map()));
    }

    private Map<String, DataFetcher> map() {
        return Map.of(
                "customers", dfe -> "s",
                "age", dfe -> 12,
                "city", dfe -> "Pune"
        );
    }*/

}