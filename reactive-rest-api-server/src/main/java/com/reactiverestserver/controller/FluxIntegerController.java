package com.reactiverestserver.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxIntegerController {

    @GetMapping(value ="/flux", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Integer> returnFlux(){

        return Flux.just(1,2,3,4,6,7)
                .delayElements(Duration.ofSeconds(1))
                .concatWith(Flux.error(new RuntimeException("Exception Occurred!")))
                .onErrorResume(err -> Flux.empty())
                .log();
    }

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Long> returnFluxStream(){

        return Flux.interval(Duration.ofSeconds(1))
                .log();

    }
}
