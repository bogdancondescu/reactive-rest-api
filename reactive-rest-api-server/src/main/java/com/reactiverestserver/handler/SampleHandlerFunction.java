package com.reactiverestserver.handler;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class SampleHandlerFunction {

    public Mono<ServerResponse> flux(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(
                        Flux.interval(Duration.ofSeconds(1))
                                .log(), Long.class
                );
    }

    public Mono<ServerResponse> mono(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.just(1)
                                .log(), Integer.class
                );
    }
}
