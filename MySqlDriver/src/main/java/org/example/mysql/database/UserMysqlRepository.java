package org.example.mysql.database;

import org.example.mysql.schema.UserMysql;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserMysqlRepository extends CrudRepository<UserMysql, Long> {

    Optional<UserMysql> findByPublicId(UUID publicId);
}
