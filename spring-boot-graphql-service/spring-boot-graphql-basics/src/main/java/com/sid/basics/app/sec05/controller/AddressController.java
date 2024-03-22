package com.sid.basics.app.sec05.controller;

import com.sid.basics.app.sec05.model.Address;
import com.sid.basics.app.sec05.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * @author Siddhant Patni
 */
@Controller
@Slf4j
public class AddressController {

    @SchemaMapping
    public Mono<Address> address(Customer customer){
        return Mono.just(
                Address.create(customer.getName() + " street", customer.getName() + " city" )
        );
    }

}