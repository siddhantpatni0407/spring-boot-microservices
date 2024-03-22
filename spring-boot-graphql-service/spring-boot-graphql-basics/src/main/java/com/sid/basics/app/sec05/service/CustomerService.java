package com.sid.basics.app.sec05.service;

import com.sid.basics.app.sec05.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author Siddhant Patni
 */
@Service
@Slf4j
public class CustomerService {

    private final Flux<Customer> flux = Flux.just(
            Customer.create(1, "Soumendu", 29, "Kolkata"),
            Customer.create(2, "Rajendra", 30, "New Delhi"),
            Customer.create(3, "Siddhant", 28, "Pune"),
            Customer.create(4, "Sachin", 26, "Bangalore")
    );

    public Flux<Customer> allCustomers() {
        if (log.isInfoEnabled()) {
            log.info("allCustomers() : Fetch all Customers - END");
        }
        return flux;
    }

}