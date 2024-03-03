package com.sid.spring.batch.app.service;


import com.sid.spring.batch.app.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class ProcessorService implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) {
        /*if (customer.getCountry().equals("United States")) {
            return customer;
        } else {
            return null;
        }*/
        return customer;
    }

}