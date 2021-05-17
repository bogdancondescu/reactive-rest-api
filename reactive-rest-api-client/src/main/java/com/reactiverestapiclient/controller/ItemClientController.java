package com.reactiverestapiclient.controller;

import com.reactiverestapiclient.domain.Item;
import com.reactiverestapiclient.service.ItemClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/client")
public class ItemClientController {

    private final ItemClientService itemClientService;

    @GetMapping(value = "/retrieve/items", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Item> getAllItemsUsingRetrieve() {

        return itemClientService.getAllItemsUsingRetrieve();
    }

    @GetMapping("/exchange/items")
    public Flux<Item> getAllItemsUsingExchange() {

        return itemClientService.getAllItemsUsingExchange();
    }

    @GetMapping("/retrieve/items/{id}")
    public Mono<Item> getOneItemUsingRetrieve(@PathVariable String id) {

        return itemClientService.getOneItemUsingRetrieve(id);
    }

    @GetMapping("/exchange/items/{id}")
    public Mono<Item> getOneItemUsingExchange(@PathVariable String id) {

        return itemClientService.getOneItemUsingExchange(id);
    }

    @PostMapping("/items")
    public Mono<Item> createItem(@RequestBody Item item) {

        Mono<Item> itemMono = Mono.just(item);
        return itemClientService.createItem(itemMono);

    }

    @DeleteMapping("/items/{id}")
    public Mono<Void> deleteItem(@PathVariable String id) {

        return itemClientService.deleteItem(id);
    }

    @PutMapping("/items/{id}")
    public Mono<Item> updateItem(@PathVariable String id,
                                 @RequestBody Item item) {

        Mono<Item> itemMono = Mono.just(item);
        return itemClientService.updateItem(id, itemMono);
    }

}
