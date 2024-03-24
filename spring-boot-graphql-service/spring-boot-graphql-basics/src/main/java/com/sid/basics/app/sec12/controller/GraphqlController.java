package com.sid.basics.app.sec12.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class GraphqlController {

    //*************** Start application in DEBUG at root level ***************

    @QueryMapping
    public Mono<String> sayHello(@Argument("name") String value) {
        return Mono.fromSupplier(() -> "Hello " + value);
    }

}