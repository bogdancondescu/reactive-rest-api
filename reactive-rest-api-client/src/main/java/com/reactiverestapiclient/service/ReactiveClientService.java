package com.reactiverestapiclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class ReactiveClientService {

    private final WebClient webClient;

    public ReactiveClientService(WebClient.Builder webClientBuilder, @Value("${reactive.server.url}") String serverUrl) {
        this.webClient = webClientBuilder.baseUrl(serverUrl).build();
    }

    public Flux<Integer> retrieveIntegerFlux(){
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

        return integers;
    }

}
