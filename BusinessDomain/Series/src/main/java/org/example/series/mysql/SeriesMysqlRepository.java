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
}
