package com.sid.basics.app.sec02.controller;

import com.sid.basics.app.sec02.model.AgeRangeFilter;
import com.sid.basics.app.sec02.model.Customer;
import com.sid.basics.app.sec02.service.CustomerService;
import com.sid.basics.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Siddhant Patni
 */
@Controller
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService service;

    @QueryMapping
    public Flux<Customer> customers() {
        log.info("customers() : Fetch all Customers - START");
        return service
                .allCustomers();
    }

    @QueryMapping
    public Mono<Customer> customerById(@Argument Integer id) {
        log.info("customerById() : Get Customers By Id - START");
        return service.customerById(id)
                .flatMap(customer -> {
                    if (log.isInfoEnabled()) {
                        log.info("customerById() : Get Customers By Id. Response - > {}", ApplicationUtils.getJSONString(customer));
                        log.info("customerById() : Get Customers By Id - END");
                    }
                    return Mono.just(customer);
                });
    }

    @QueryMapping
    public Flux<Customer> customersNameContains(@Argument String name) {
        return service.nameContains(name);
    }

    @QueryMapping
    public Flux<Customer> customersByAgeRange(@Argument AgeRangeFilter filter) {
        return service.withinAge(filter);
    }

}