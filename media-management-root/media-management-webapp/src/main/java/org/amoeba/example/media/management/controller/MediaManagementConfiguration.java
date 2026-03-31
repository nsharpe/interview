package org.amoeba.example.media.management.controller;

import org.amoeba.example.series.SeriesService;
import org.amoeba.example.series.episode.EpisodeService;
import org.amoeba.example.series.episode.mysql.EpisodeMysqlRepository;
import org.amoeba.example.series.mysql.SeriesMysqlRepository;
import org.amoeba.example.series.season.SeasonService;
import org.amoeba.example.series.season.mysql.SeasonMysqlRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@EntityScan(basePackages = "org.amoeba.example")
public class MediaManagementConfiguration
{
    @Bean
    public SeriesService seriesService(SeriesMysqlRepository repository){
        return new SeriesService(repository);
    }

    @Bean
    public SeasonService seasonService(SeasonMysqlRepository repository, SeriesMysqlRepository seriesRepository){
        return new SeasonService(repository,seriesRepository);
    }

    @Bean
    public EpisodeService episodeService(EpisodeMysqlRepository repository, SeasonService seasonService){
        return new EpisodeService(seasonService,repository);
    }
}
