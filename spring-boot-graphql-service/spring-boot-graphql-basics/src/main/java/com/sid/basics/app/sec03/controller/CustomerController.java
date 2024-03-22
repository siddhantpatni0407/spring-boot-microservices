package com.sid.basics.app.sec03.controller;

import com.sid.basics.app.sec03.model.Customer;
import com.sid.basics.app.sec03.service.CustomerService;
import com.sid.basics.app.sec03.model.CustomerOrder;
import com.sid.basics.app.sec03.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

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

    @SchemaMapping(typeName = "Customer")
    public Flux<CustomerOrder> orders(Customer customer, @Argument Integer limit) {
        log.info("orders() : Orders method invoked for {}", customer.getName());
        return orderService.ordersByCustomerName(customer.getName())
                .take(limit);
    }

}