package com.reactiverestapiclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ReactiveClientService {

    private final WebClient webClient;

    public ReactiveClientService(WebClient.Builder webClientBuilder, @Value("${reactive.server.url}") String serverUrl) {
        this.webClient = webClientBuilder.baseUrl(serverUrl).build();
    }

    public Flux<Integer> retrieveIntegerFlux(){
       return webClient.get().uri("/flux")
                .retrieve()
                .bodyToFlux(Integer.class)
                .log();
    }

}
