package com.sid.basics.app.sec11.controller;

import com.sid.basics.app.sec11.model.Book;
import com.sid.basics.app.sec11.model.Electronics;
import com.sid.basics.app.sec11.model.FruitDto;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@Controller
public class SearchEngineController {

    List<Object> list = List.of(
            FruitDto.create("Banana", LocalDate.now().plusDays(3)),
            FruitDto.create("Apple", LocalDate.now().plusDays(5)),
            Electronics.create("Mac-Book", 600, "APPLE"),
            Electronics.create("Phone", 400, "SAMSUNG"),
            Book.create("Java", "Siddhant")
    );

    @QueryMapping
    public Flux<Object> search() {
        return Mono.fromSupplier(() -> new ArrayList<>(list))
                .doOnNext(Collections::shuffle)
                .flatMapIterable(Function.identity())
                .take(ThreadLocalRandom.current().nextInt(0, list.size()));
    }

}