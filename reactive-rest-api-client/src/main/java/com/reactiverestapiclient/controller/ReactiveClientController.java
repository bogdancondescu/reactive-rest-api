package com.reactiverestapiclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@Slf4j
public class ReactiveClientController {

    WebClient webClient = WebClient.create("http://localhost:9090");

    @GetMapping(value = "/client/retrieve", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Integer> getAllItemsUsingRetrieve() {

        Flux<Integer> integers = webClient.get().uri("/flux")
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {

                    Mono<String> errorMono = clientResponse.bodyToMono(String.class);
                    return errorMono.flatMap((errorMessage) -> {
                        log.error("The error Message is : " + errorMessage);
                        return Mono.error(new RuntimeException(errorMessage));
                    });
                })
                .bodyToFlux(Integer.class)
                .log();

        integers.subscribe(k -> {
            log.info(k.toString());
        }, err ->{
            log.error("Blah blah :" + err.toString());
        });


        return integers;
    }

}
