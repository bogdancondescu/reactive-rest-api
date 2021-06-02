package com.reactiverestserver.controller;

import com.reactiverestserver.domain.Item;
import com.reactiverestserver.repository.ItemReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FluxIntegerController {

    @GetMapping(value ="/flux", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Integer> returnFlux(){

        return Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1))
                .concatWith(Flux.error(new RuntimeException("Exception Occurred!")))
                .concatWith(Flux.just(5,6))
                .onErrorResume(err -> Flux.empty())
                .log();
    }

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Long> returnFluxStream(){


        return Flux.interval(Duration.ofSeconds(1))
                .log();

    }
}
