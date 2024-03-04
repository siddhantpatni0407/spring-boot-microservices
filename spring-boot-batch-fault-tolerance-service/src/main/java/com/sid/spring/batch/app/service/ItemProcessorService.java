package com.sid.spring.batch.app.service;

import com.sid.spring.batch.app.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

@SuppressWarnings("PMD")
public class ItemProcessorService implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) {
        int age = Integer.parseInt(customer.getAge());
        if (age >= 18) {
            return customer;
        }
        return null;
    }

}