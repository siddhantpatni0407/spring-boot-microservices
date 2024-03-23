package com.sid.basics.app.sec06.controller;

import com.sid.basics.app.sec06.model.CustomerWithOrder;
import com.sid.basics.app.sec06.service.CustomerService;
import com.sid.basics.app.sec06.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class CustomerRestController {

    @Autowired
    private CustomerService service;

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/v1/graphql-basics/customers")
    public Flux<CustomerWithOrder> customerOrders() {
        return this.service.allCustomers()
                .flatMap(customer ->
                        this.orderService.ordersByCustomerName(customer.getName())
                                .collectList()
                                .map(l -> CustomerWithOrder.create(customer.getId(), customer.getName(), customer.getAge(), customer.getCity(), l))
                );
    }

}