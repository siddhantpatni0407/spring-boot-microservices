package com.sid.basics.app.sec08.controller;

import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class FieldGlobPatternController {

    @QueryMapping
    public Mono<Object> level1(DataFetchingFieldSelectionSet selectionSet) {
        log.info("{}", selectionSet.contains("level2"));
        log.info("{}", selectionSet.contains("level2/level3"));
        log.info("{}", selectionSet.contains("**/level3"));
        log.info("{}", selectionSet.contains("level2/*/level5")); // false
        log.info("{}", selectionSet.contains("level2/**/level5")); // true - Use ** for multiple levels
        return Mono.empty();
    }

}