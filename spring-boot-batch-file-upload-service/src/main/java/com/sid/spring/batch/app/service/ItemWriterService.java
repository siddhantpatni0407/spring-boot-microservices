package com.sid.spring.batch.app.service;

import com.sid.spring.batch.app.entity.Customer;
import com.sid.spring.batch.app.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ItemWriterService implements ItemWriter<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void write(List<? extends Customer> list) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("write() : Thread Name -> {}", Thread.currentThread().getName());
        }
        customerRepository.saveAll(list);
    }

}