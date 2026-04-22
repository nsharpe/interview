package org.amoeba.example.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRespoitory extends PagingAndSortingRepository<CommentPostgres, Long>,
        CrudRepository<CommentPostgres,Long> {

    Optional<CommentPostgres> findByPublicId(UUID publicId);

    void deleteByPublicId(UUID uuid);

    Page<CommentPostgres> findByRecordIdAndRecordType(UUID recordId, String recordType, Pageable pageable);

}
