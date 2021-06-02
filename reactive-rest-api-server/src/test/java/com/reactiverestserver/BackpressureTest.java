package com.reactiverestserver;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class BackpressureTest  {


    @Test
    public void whenRequestingChunks10_thenMessagesAreReceived() {
        Flux<Integer> request = Flux.range(1, 50);

        request.subscribe(
                System.out::println,
                err -> err.printStackTrace(),
                () -> System.out.println("All 50 items have been successfully processed!!!"),
                subscription -> {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Requesting the next 5 elements!!!");
                        subscription.request(5);
                    }
                }
        );

        StepVerifier.create(request)
                .expectSubscription()
                .expectNextCount(50)
                .verifyComplete();
    }
}
