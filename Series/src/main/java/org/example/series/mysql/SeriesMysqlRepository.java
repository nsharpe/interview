package org.example.series.mysql;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SeriesMysqlRepository extends CrudRepository<SeriesMysql, Long> {

    Optional<SeriesMysql> findByPublicId(UUID id);
}
