package org.example.media.management.sdk.configuration;

import org.example.media.management.sdk.api.EpisodeControllerApi;
import org.example.media.management.sdk.api.SeasonControllerApi;
import org.example.media.management.sdk.api.SeriesControllerApi;
import org.example.media.management.sdk.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MediaManagementConfiguration {

    @Bean
    @Qualifier("mediaMangementApiClient")
    public ApiClient apiClient(@Value("${media.management.host}") String host,
                               @Value("${media.management.port}") int port) {

        ApiClient apiClient = new ApiClient();
        apiClient.setHost(host);
        apiClient.setPort(port);

        return apiClient;
    }

    @Bean
    public SeasonControllerApi seasonControllerApi(@Qualifier("mediaMangementApiClient") ApiClient apiClient){
        return new SeasonControllerApi(apiClient);
    }

    @Bean
    public SeriesControllerApi seriesControllerApi(@Qualifier("mediaMangementApiClient") ApiClient apiClient){
        return new SeriesControllerApi(apiClient);
    }

    @Bean
    public EpisodeControllerApi episodeControllerApi(@Qualifier("mediaMangementApiClient") ApiClient apiClient){
        return new EpisodeControllerApi(apiClient);
    }
}
