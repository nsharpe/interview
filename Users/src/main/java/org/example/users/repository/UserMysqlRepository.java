package org.example.users.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserMysqlRepository extends CrudRepository<UserMysql, Long> {

    Optional<UserMysql> findByPublicId(UUID publicId);
}
