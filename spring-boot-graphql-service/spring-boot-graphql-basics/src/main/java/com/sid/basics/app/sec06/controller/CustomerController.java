package com.sid.basics.app.sec06.controller;

import com.sid.basics.app.sec06.model.CustomerWithOrder;
import com.sid.basics.app.sec06.service.CustomerOrderDataFetcher;
import com.sid.basics.app.sec06.service.CustomerService;
import com.sid.basics.app.sec06.service.OrderService;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CustomerOrderDataFetcher customerOrderDataFetcher;

    // @QueryMapping
    /*
    @SchemaMapping(typeName = "Query")
    public Flux<Customer> customers() {
        return service.allCustomers();
    }

    @SchemaMapping(typeName = "Customer")
    public Flux<CustomerOrder> orders(Customer customer) {
        log.info("orders() : Orders method invoked for {}", customer.getName());
        return orderService.ordersByCustomerName(customer.getName());
    }*/

    @SchemaMapping(typeName = "Query")
    public Flux<CustomerWithOrder> customers(DataFetchingFieldSelectionSet selectionSet) {
        return customerOrderDataFetcher.customerOrders(selectionSet);
    }

}