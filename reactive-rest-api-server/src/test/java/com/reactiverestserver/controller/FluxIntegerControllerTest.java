package com.reactiverestserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(FluxIntegerController.class)
class FluxIntegerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void fluxEndpointTest(){

        Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();


        List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);

        EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient
                .get().uri("/flux")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
//                .consumeWith((response      ) -> {
//                    assertEquals(expectedIntegerList, response.getResponseBody());
//                });
                .returnResult();

        assertEquals(expectedIntegerList,entityExchangeResult.getResponseBody());


    }

    @Test
    void fluxEndpointSizeResponseTest() {
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_NDJSON)
                .expectBodyList(Integer.class)
                .hasSize(4);
    }

    @Test
    void fluxStreamEndpointTest() {
        Flux<Long> longStreamFlux = webTestClient.get().uri("/fluxstream")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();


        StepVerifier.create(longStreamFlux)
                .expectNext(0l)
                .expectNext(1l)
                .expectNext(2l)
                .expectNext(3l)
                .thenCancel()
                .verify();
    }
}