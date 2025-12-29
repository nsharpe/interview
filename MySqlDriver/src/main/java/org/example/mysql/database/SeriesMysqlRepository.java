package org.example.mysql.database;

import org.example.mysql.schema.SeriesMysql;
import org.example.mysql.schema.UserMysql;
import org.springframework.data.repository.CrudRepository;

public interface SeriesMysqlRepository extends CrudRepository<SeriesMysql, Long> {
}
