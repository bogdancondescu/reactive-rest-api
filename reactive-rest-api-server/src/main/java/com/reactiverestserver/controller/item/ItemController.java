package com.reactiverestserver.controller.item;

import com.reactiverestserver.domain.Item;
import com.reactiverestserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Item> getAllItems(){
       return service.findAllItems().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Item>> getOneItem(@PathVariable String id){

        return service.findItemById(id)
                .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> createItem(@RequestBody Item item){

        return service.createItem(item);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteItem(@PathVariable String id){

        return service.removeItem(id);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Item>> updateItem(@PathVariable String id,@RequestBody Item item){

        return service.updateItem(id, item)
                .map(updatedItem -> new ResponseEntity<>(updatedItem, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
