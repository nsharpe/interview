package org.example.users.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserCrudRespoitory extends PagingAndSortingRepository<UserPostgres, Long>,
        CrudRepository<UserPostgres,Long> {

    Optional<UserPostgres> findByPublicId(UUID publicId);

    void deleteByPublicId(UUID uuid);

}
