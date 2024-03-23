package com.sid.basics.app.sec05.controller;

import com.sid.basics.app.sec05.model.Customer;
import com.sid.basics.app.sec05.service.CustomerService;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/**
 * @author Siddhant Patni
 */
@Controller
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService service;

    @QueryMapping
    public Flux<Customer> customers(DataFetchingEnvironment environment, DataFetchingFieldSelectionSet selectionSet) {
        log.info("customers() : Fetch all Customers - START");
        //log.info("customers() : customer -> {}", environment.getDocument());
        //log.info("customers() : getOperationDefinition -> {}", environment.getOperationDefinition());
        log.info("customers() : customer -> {}", selectionSet.getFields());
        return service
                .allCustomers();
    }

}