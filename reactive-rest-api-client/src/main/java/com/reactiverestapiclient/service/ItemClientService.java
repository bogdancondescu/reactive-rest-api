package com.reactiverestapiclient.service;

import com.reactiverestapiclient.domain.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemClientService {

    private final WebClient webClient;

    public ItemClientService(WebClient.Builder webClientBuilder, @Value("${reactive.server.url}") String serverUrl) {
        this.webClient = webClientBuilder.baseUrl(serverUrl).build();
    }

    public Flux<Item> getAllItemsUsingRetrieve() {

        return webClient.get().uri("/items")
                .retrieve()
                .bodyToFlux(Item.class)
                .log("Retrieve all items: ");
    }

    public Flux<Item> getAllItemsUsingExchange() {

        return webClient.get().uri("/items")
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Item.class))
                .log("Exchange all items : ");
    }

    public Mono<Item> getOneItemUsingRetrieve(String id) {

        return webClient.get().uri("/items/{id}", id)
                .retrieve()
                .bodyToMono(Item.class)
                .log("Retrieve single Item: ");
    }

    public Mono<Item> getOneItemUsingExchange( String id) {

        return webClient.get().uri("/items/{id}", id)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Item.class))
                .log("Exchange single Item: ");
    }

    public Mono<Item> createItem(Mono<Item> item) {

        return webClient.post().uri("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(item, Item.class)
                .retrieve()
                .bodyToMono(Item.class)
                .log("Created item is: ");

    }

    public Mono<Void> deleteItem(String id) {

        return webClient.delete().uri("/items/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .log();
    }

    public Mono<Item> updateItem(String id, Mono<Item> item) {

        return webClient.put().uri("/items/{id}", id)
                .body(item, Item.class)
                .retrieve()
                .bodyToMono(Item.class)
                .log("Updated Item is: ");
    }
}
