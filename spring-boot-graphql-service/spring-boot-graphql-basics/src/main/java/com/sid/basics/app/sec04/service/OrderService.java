package com.sid.basics.app.sec04.service;

import com.sid.basics.app.sec04.model.Customer;
import com.sid.basics.app.sec04.model.CustomerOrder;
import com.sid.basics.app.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class OrderService {

    private final Map<String, List<CustomerOrder>> map = Map.of(
            "Soumendu", List.of(
                    CustomerOrder.create(UUID.randomUUID(), "Soumendu-product-1"),
                    CustomerOrder.create(UUID.randomUUID(), "Soumendu-product-2")
            ),
            "Siddhant", List.of(
                    CustomerOrder.create(UUID.randomUUID(), "Siddhant-product-1"),
                    CustomerOrder.create(UUID.randomUUID(), "Siddhant-product-2"),
                    CustomerOrder.create(UUID.randomUUID(), "Siddhant-product-3")
            )
    );

    public Flux<CustomerOrder> ordersByCustomerName(String name) {
        log.info("ordersByCustomerName() : ordersByCustomerName method invoked.  name -> {}", name);
        return Flux.fromIterable(map.getOrDefault(name, Collections.emptyList()));
    }

    /*public Flux<List<CustomerOrder>> ordersByCustomerName(List<String> names) {
        return Flux.fromIterable(names)
                .map(name -> map.getOrDefault(name, Collections.emptyList()));
    }*/

    public Flux<List<CustomerOrder>> ordersByCustomerName(List<String> names) {
        log.info("ordersByCustomerName() : ordersByCustomerName method invoked.  customerList -> {}", ApplicationUtils.getJSONString(names));
        return Flux.fromIterable(names)
                //.flatMap(this::fetchOrders); // This will give error -> Error : org.dataloader.impl.DataLoaderAssertionException: The size of the promised values MUST be the same size as the key list
                //.flatMap(name -> fetchOrders(name).defaultIfEmpty(Collections.emptyList()));  // We will get different result if we have delay as mentioned in fetchOrders method. So we will use flatMapSequential
                .flatMapSequential(name -> fetchOrders(name).defaultIfEmpty(Collections.emptyList()));
    }

    public Mono<List<CustomerOrder>> fetchOrders(String name) {
        log.info("fetchOrders() : fetchOrders method invoked.  name -> {}", name);
        return Mono.justOrEmpty(map.get(name))
                .delayElement(Duration.ofMillis(ThreadLocalRandom.current().nextInt(0, 500)));
    }

    public Mono<Map<Customer, List<CustomerOrder>>> fetchOrdersAsMap(List<Customer> customers) {
        log.info("fetchOrdersAsMap() : fetchOrdersAsMap method invoked.  customers -> {}", ApplicationUtils.getJSONString(customers));
        return Flux.fromIterable(customers)
                .map(customer -> Tuples.of(customer, map.getOrDefault(customer.getName(), Collections.emptyList())))
                .collectMap(
                        Tuple2::getT1,
                        Tuple2::getT2
                );
    }

}