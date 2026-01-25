package org.example.users.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserCrudRespoitory extends CrudRepository<UserPostgres, Long> {

    Optional<UserPostgres> findByPublicId(UUID publicId);

    void deleteByPublicId(UUID uuid);
}
