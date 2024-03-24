package com.sid.basics.app.sec11.config;

import com.sid.basics.app.sec11.model.FruitDto;
import graphql.schema.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class TypeResolverConfig {

    @Bean
    public RuntimeWiringConfigurer configurer(TypeResolver resolver) {
        return c -> c.type("Result", b -> b.typeResolver(resolver));
    }

    @Bean
    public TypeResolver typeResolver() {
        ClassNameTypeResolver resolver = new ClassNameTypeResolver();
        resolver.addMapping(FruitDto.class, "Fruit");
        return resolver;
    }

}