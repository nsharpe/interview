package org.example.qa.sdk;

import org.example.qa.sdk.api.UserGeneratorControllerApi;
import org.example.qa.sdk.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class QaEndpointSdkConfig {

    @Bean
    public UserGeneratorControllerApi userGeneratorControllerApi(WebClient.Builder webclientBuilder,
                                                                 @Value("${qa.endpoint.host}") String host,
                                                                 @Value("${qa.endpoint.port}") int port) {
        return new UserGeneratorControllerApi(new ApiClient(
                webclientBuilder.build()
        ).setBasePath("http://" + host + ":" + port));
    }
}
