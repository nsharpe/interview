package org.example.media.management.controller;

import org.example.series.SeriesService;
import org.example.series.mysql.SeriesMysqlRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableCaching
@EnableJpaRepositories(basePackages = "org.example")
@EntityScan(basePackages = "org.example")
public class MediaManagementConfiguration
{
    @Bean
    public SeriesService seriesService(SeriesMysqlRepository repository){
        return new SeriesService(repository);
    }
}
