package com.sid.basics.app.sec06.service;

import com.sid.basics.app.sec06.model.CustomerWithOrder;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.UnaryOperator;

/**
 * @author Siddhant Patni
 */
@Service
@Slf4j
public class CustomerOrderDataFetcher {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    public Flux<CustomerWithOrder> customerOrders(DataFetchingFieldSelectionSet selectionSet) {
        var includeOrders = selectionSet.contains("orders");
        log.info("customerOrders() : includeOrders -> {}", includeOrders);
        return this.customerService.allCustomers()
                .map(customer -> CustomerWithOrder.create(customer.getId(), customer.getName(), customer.getAge(), customer.getCity(), Collections.emptyList()))
                .transform(updateOrdersTransformer(includeOrders));
    }

    private UnaryOperator<Flux<CustomerWithOrder>> updateOrdersTransformer(boolean includeOrders) {
        return includeOrders ? f -> f.flatMapSequential(this::fetchOrders) : f -> f;
    }

    private Mono<CustomerWithOrder> fetchOrders(CustomerWithOrder customerWithOrder) {
        return orderService.ordersByCustomerName(customerWithOrder.getName())
                .collectList()
                .doOnNext(customerWithOrder::setOrders)
                .thenReturn(customerWithOrder);
    }

}