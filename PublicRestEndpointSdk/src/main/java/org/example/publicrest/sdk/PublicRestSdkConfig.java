package org.example.publicrest.sdk;

import org.example.publicrest.sdk.api.UserControllerApi;
import org.example.publicrest.sdk.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class PublicRestSdkConfig {


    @Bean
    @Qualifier("publicRestApiClient")
    public ApiClient publicRestApiClient(@Value("${publicrest.host}") String host,
                                         @Value("${publicrest.port}") int port,
                                         WebClient.Builder webClientBuilder) {

        ApiClient apiClient = new ApiClient(webClientBuilder
                .filter((request, next) -> next.exchange(request)
                        .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                                .filter(throwable -> throwable instanceof IOException ||
                                        throwable.getMessage().contains("Connection reset by peer"))) // Retries on closed connections
                )
                .build())
                .setBasePath("http://"+host+":"+port);

        return apiClient;
    }

    @Bean
    public UserControllerApi userControllerApi(@Qualifier("publicRestApiClient") ApiClient apiClient){
        return new UserControllerApi(apiClient);
    }
}
