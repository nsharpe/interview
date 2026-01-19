package org.example.series.episode.mysql;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface EpisodeMysqlRepository extends CrudRepository<EpisodeMysql,Long> {

    @Query("SELECT episode FROM EpisodeMysql episode LEFT JOIN FETCH episode.season season LEFT JOIN FETCH season.series")
    Optional<EpisodeMysql> findByPublicId(UUID id);

    void deleteByPublicId(UUID publicId);
}
