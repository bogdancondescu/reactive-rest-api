package com.reactiverestserver.repository;

import com.reactiverestserver.domain.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemReactiveRepository extends ReactiveMongoRepository<Item,String> {

    Mono<Item> findByDescription(String description);

    Flux<Item> findAllByPrice(double price);
}
