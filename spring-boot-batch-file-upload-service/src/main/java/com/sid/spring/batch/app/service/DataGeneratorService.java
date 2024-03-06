package com.sid.spring.batch.app.service;

import com.sid.spring.batch.app.entity.Customer;
import com.sid.spring.batch.app.repository.CustomerRepository;
import com.sid.spring.batch.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@SuppressWarnings("PMD")
public class DataGeneratorService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllData() {
        return customerRepository.findAll();
    }

}