package org.example.mysql.database;

import org.example.mysql.schema.UserMysql;
import org.springframework.data.repository.CrudRepository;

public interface UserMysqlRepository extends CrudRepository<UserMysql, Long> {
}
