package org.example.series.season.mysql;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SeasonMysqlRepository extends CrudRepository<SeasonMysql,Long> {

    Optional<SeasonMysql> findByPublicId(UUID uuid);

    void deleteByPublicId(UUID uuid);
}
