package com.sid.basics.app.sec12.config;

import graphql.ExecutionInput;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.execution.preparsed.PreparsedDocumentProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Configuration
@Slf4j
public class OperationCachingConfig {

    /*
            request body
            exe doc
            parse
            validation
            exe doc

            suggestion:
            use variables along with operation mame
            prefer this: https://github.com/ben-manes/caffeine
     */

    @Bean
    public GraphQlSourceBuilderCustomizer sourceBuilderCustomizer(PreparsedDocumentProvider provider) {
        return c -> c.configureGraphQl(builder -> builder.preparsedDocumentProvider(provider));
    }

    @Bean
    public PreparsedDocumentProvider provider() {
        Map<String, PreparsedDocumentEntry> map = new ConcurrentHashMap<>();
        return new PreparsedDocumentProvider() {
            @Override
            public PreparsedDocumentEntry getDocument(ExecutionInput executionInput, Function<ExecutionInput, PreparsedDocumentEntry> parseAndValidateFunction) {
                return map.computeIfAbsent(executionInput.getQuery(), key -> {
                    log.info("provider() : Not found : {}", key);
                    var r = parseAndValidateFunction.apply(executionInput);
                    log.info("provider() : Caching : {}", r);
                    return r;
                });
            }
        };
    }

}