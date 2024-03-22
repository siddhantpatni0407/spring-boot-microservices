package com.sid.basics.app.sec02.service;

import com.sid.basics.app.sec02.model.AgeRangeFilter;
import com.sid.basics.app.sec02.model.Customer;
import com.sid.basics.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<Customer> customerById(Integer id) {
        return flux
                .filter(customer -> customer.getId().equals(id))
                .next();
    }

    public Flux<Customer> nameContains(String name) {
        return flux
                .filter(customer -> customer.getName().contains(name));
    }

    public Flux<Customer> withinAge(AgeRangeFilter filter) {
        return flux
                .filter(customer -> customer.getAge() >= filter.getMinAge() && customer.getAge() <= filter.getMaxAge());
    }

}