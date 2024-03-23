package com.sid.basics.app.sec07.service;

import com.sid.basics.app.sec07.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class CustomerService {

    private final Flux<Customer> flux = Flux.just(
            Customer.create(1, "Soumendu", 29, "Kolkata"),
            Customer.create(2, "Rajendra", 10, "New Delhi"),
            Customer.create(3, "Siddhant", 15, "Pune"),
            Customer.create(4, "Sachin", 26, "Bangalore")
    );

    public Flux<Customer> allCustomers() {
        return flux.delayElements(Duration.ofSeconds(1))
                .doOnNext(customer -> print("customer " + customer.getName()));
    }

    private void print(String message) {
        log.info("{} : {}", LocalDateTime.now(), message);
    }

}