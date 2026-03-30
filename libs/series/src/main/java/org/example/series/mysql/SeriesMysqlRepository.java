package org.example.series.mysql;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeriesMysqlRepository extends CrudRepository<SeriesMysql, Long>, PagingAndSortingRepository<SeriesMysql,Long> {

    Optional<SeriesMysql> findByPublicId(UUID id);

    void deleteByPublicId(UUID publicId);

    @Query("SELECT sm.publicId FROM SeriesMysql sm ORDER BY sm.timeStamp.creationTimestamp DESC")
    List<UUID> findAllPublicId();

    @Query("""
            SELECT episode.publicId FROM EpisodeMysql episode
            LEFT JOIN episode.season season
            LEFT JOIN season.series series
            WHERE series.publicId=:seriesId
            AND season.order = (SELECT MIN(innerSeason.order) FROM SeasonMysql innerSeason WHERE innerSeason.series.id=series.id)
            AND episode.order = (SELECT MIN(innerEpisode.order) FROM EpisodeMysql innerEpisode WHERE innerEpisode.season.id = season.id)
            """)
    UUID firstEpisode(UUID seriesId);
}
