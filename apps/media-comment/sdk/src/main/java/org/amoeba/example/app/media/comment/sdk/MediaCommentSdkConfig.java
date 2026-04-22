package org.amoeba.example.app.media.comment.sdk;

import org.amoeba.example.app.media.comment.sdk.api.CommentControllerApi;
import org.amoeba.example.app.media.comment.sdk.api.MediaCommentControllerApi;
import org.amoeba.example.app.media.comment.sdk.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class MediaCommentSdkConfig {

    @Bean
    @Qualifier("commentRestApiClient")
    public ApiClient commentRestApiClient(@Value("${media.comment.host}") String host,
                                              @Value("${media.comment.port}") int port,
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
    public MediaCommentControllerApi mediaCommentControllerApi(@Qualifier("commentRestApiClient") ApiClient apiClient){
        return new MediaCommentControllerApi(apiClient);
    }

    @Bean
    public CommentControllerApi commentControllerApi(@Qualifier("commentRestApiClient") ApiClient apiClient){
        return new CommentControllerApi(apiClient);
    }
}
