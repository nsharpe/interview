package org.example.mysql.database;

import org.example.mysql.schema.SeriesMysql;
import org.springframework.data.repository.CrudRepository;

public interface SeriesMysqlRepository extends CrudRepository<SeriesMysql, Long> {
}
