package org.example.test.data.config;

import org.example.admin.sdk.api.UserAdminControllerApi;
import org.example.admin.sdk.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class AdminSdkConfig {

    @Bean
    @Qualifier("adminApiClient")
    public ApiClient adminApiClient(@Value("${admin.endpoint.host}")String host,
                                    @Value("${admin.endpoint.port}")int port,
                                    WebClient.Builder webClientBuilder){
        return new ApiClient(webClientBuilder
                .filter((request, next) -> next.exchange(request)
                        .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                                .filter(throwable -> throwable instanceof IOException ||
                                        throwable.getMessage().contains("Connection reset by peer"))) // Retries on closed connections
                )
                .build())
                .setBasePath("http://"+host+":"+port+"/admin");
    }

    @Bean
    public UserAdminControllerApi userAdminControllerApi(ApiClient apiClient){
        return new UserAdminControllerApi(apiClient);
    }

}
