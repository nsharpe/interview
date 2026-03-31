package org.amoeba.example.metric.media.sdk;

import org.amoeba.example.media.metric.sdk.api.MediaPerformanceControllerApi;
import org.amoeba.example.media.metric.sdk.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class MediaMetricSdkConfig {


    @Bean
    @Qualifier("mediaMetricRestApiClient")
    public ApiClient mediaMetricRestApiClient(@Value("${media.metric.host}") String host,
                                         @Value("${media.metric.port}") int port,
                                         WebClient.Builder webClientBuilder) {

        return new ApiClient(webClientBuilder
                .filter((request, next) -> next.exchange(request)
                        .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                                .filter(throwable -> throwable instanceof IOException ||
                                        throwable.getMessage().contains("Connection reset by peer"))) // Retries on closed connections
                )
                .build())
                .setBasePath("http://"+host+":"+port);
    }

    @Bean
    public MediaPerformanceControllerApi mediaPerformanceControllerApi(@Qualifier("mediaMetricRestApiClient") ApiClient apiClient){
        return new MediaPerformanceControllerApi(apiClient);
    }
}
