package com.sid.basics.app.sec10.controller;

import com.sid.basics.app.sec10.model.Book;
import com.sid.basics.app.sec10.model.Electronics;
import com.sid.basics.app.sec10.model.Fruit;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Controller
public class ProductController {

    @QueryMapping
    public Flux<Object> products() {
        return Flux.just(
                Fruit.create("banana", 1, LocalDate.now().plusDays(3)),
                Fruit.create("apple", 2, LocalDate.now().plusDays(5)),
                Electronics.create("mac book", 600, "APPLE"),
                Electronics.create("phone", 400, "SAMSUNG"),
                Book.create("java", 40, "venkat")
        );
    }

}