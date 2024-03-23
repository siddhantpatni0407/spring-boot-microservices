package com.sid.basics.app.sec06.service;

import com.sid.basics.app.sec06.model.CustomerOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        return Flux.fromIterable(map.getOrDefault(name, Collections.emptyList()))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(customerOrder -> print("order for " + name));
    }

    private void print(String message) {
        log.info("{} : {}", LocalDateTime.now(), message);
    }

}