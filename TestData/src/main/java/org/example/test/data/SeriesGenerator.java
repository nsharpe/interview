package org.example.test.data;

import org.springframework.web.reactive.function.client.WebClient;

public class SeriesGenerator {

    private final WebClient webClient;

    public SeriesGenerator(WebClient.Builder webClientBuilder) {

        this.webClient = webClientBuilder
                .build();
    }
}
