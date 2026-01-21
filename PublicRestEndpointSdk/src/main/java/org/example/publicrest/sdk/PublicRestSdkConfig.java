package org.example.publicrest.sdk;

import org.example.publicrest.sdk.api.UserControllerApi;
import org.example.publicrest.sdk.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublicRestSdkConfig {


    @Bean
    @Qualifier("publicRestApiClient")
    public ApiClient publicRestApiClient(@Value("${publicrest.host}") String host,
                               @Value("${publicrest.port}") int port) {

        ApiClient apiClient = new ApiClient();
        apiClient.setHost(host);
        apiClient.setPort(port);

        return apiClient;
    }

    @Bean
    public UserControllerApi userControllerApi(@Qualifier("publicRestApiClient") ApiClient apiClient){
        return new UserControllerApi(apiClient);
    }
}
