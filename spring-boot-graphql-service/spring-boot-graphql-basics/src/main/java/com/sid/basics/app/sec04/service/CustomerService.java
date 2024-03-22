package com.sid.basics.app.sec04.service;

import com.sid.basics.app.sec04.model.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CustomerService {

    private final Flux<Customer> flux = Flux.just(
            Customer.create(1, "Soumendu", 29, "Kolkata"),
            Customer.create(2, "Rajendra", 10, "New Delhi"),
            Customer.create(3, "Siddhant", 15, "Pune"),
            Customer.create(4, "Sachin", 26, "Bangalore")
    );

    public Flux<Customer> allCustomers() {
        return flux;
    }

}
