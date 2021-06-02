package com.reactiverestapiclient.controller;

import com.reactiverestapiclient.service.ReactiveClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReactiveClientController {

    private final ReactiveClientService reactiveClientService;

    @GetMapping(value = "/client/retrieve", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Integer> getAllItemsUsingRetrieve() {

        Flux<Integer> integerFlux = reactiveClientService.retrieveIntegerFlux();
        integerFlux.subscribe(k -> {
            log.info(k.toString());
        }, err -> {
            log.error(err.toString());
        }, () -> {
            log.info("complete");
        });

        return integerFlux;
    }

}
