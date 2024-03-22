package com.sid.basics.app.sec04.controller;

import com.sid.basics.app.sec04.model.Customer;
import com.sid.basics.app.sec04.model.CustomerOrder;
import com.sid.basics.app.sec04.service.CustomerService;
import com.sid.basics.app.sec04.service.OrderService;
import com.sid.basics.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Autowired
    private OrderService orderService;

    // @QueryMapping
    @SchemaMapping(typeName = "Query")
    public Flux<Customer> customers() {
        return service.allCustomers();
    }

    // Fixing N + 1 problem
    /*@BatchMapping(typeName = "Customer")
    public Flux<List<CustomerOrder>> orders(List<Customer> customerList) {
        log.info("orders() : Orders method invoked.  customerList -> {}", ApplicationUtils.getJSONString(customerList));
        return orderService.ordersByCustomerName(
                customerList.stream().map(Customer::getName).collect(Collectors.toList())

        );
    }*/

    @BatchMapping(typeName = "Customer")
    public Mono<Map<Customer, List<CustomerOrder>>> orders(List<Customer> customerList) {
        log.info("orders() : Orders method invoked.  customerList -> {}", ApplicationUtils.getJSONString(customerList));
        return orderService.fetchOrdersAsMap(customerList);
    }

}