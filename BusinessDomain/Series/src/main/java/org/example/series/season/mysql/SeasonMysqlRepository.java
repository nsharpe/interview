package org.example.series.season.mysql;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SeasonMysqlRepository extends CrudRepository<SeasonMysql,Long> {

    @Query("""
                        SELECT season
                        FROM SeasonMysql season
                        LEFT JOIN FETCH season.series series
                        WHERE season.publicId=:uuid
            """)
    Optional<SeasonMysql> findByPublicId(UUID uuid);

    void deleteByPublicId(UUID uuid);
}
