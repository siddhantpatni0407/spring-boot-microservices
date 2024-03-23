package com.sid.basics.app.sec05.controller;

import com.sid.basics.app.sec05.model.Account;
import com.sid.basics.app.sec05.model.Address;
import com.sid.basics.app.sec05.model.Customer;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Siddhant Patni
 */
@Controller
@Slf4j
public class AccountController {

    @SchemaMapping
    public Mono<Account> account(Customer customer, DataFetchingFieldSelectionSet selectionSet) {
        log.info("account() : account -> {}", selectionSet.getFields());
        var type = ThreadLocalRandom.current().nextBoolean() ? "CURRENT" : "SAVING";
        return Mono.just(
                Account.create(
                        UUID.randomUUID(),
                        ThreadLocalRandom.current().nextInt(1, 1000),
                        type
                )
        );
    }

}