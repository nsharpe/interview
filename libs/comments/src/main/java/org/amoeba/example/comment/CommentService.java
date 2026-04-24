package org.amoeba.example.comment;

import lombok.RequiredArgsConstructor;
import org.amoeba.example.comment.repository.CommentPostgres;
import org.amoeba.example.comment.repository.CommentRespoitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRespoitory commentRespoitory;

    public Optional<CommentPostgres> get(UUID id){
        return commentRespoitory.findByPublicId(id);
    }

    public CommentPostgres createComment(CommentPostgres userModel){
        return commentRespoitory.save(userModel);
    }

    @Transactional
    public void delete(UUID id){
        commentRespoitory.deleteByPublicId(id);
    }

    public Page<CommentPostgres> getAll(Pageable pageable){
        return commentRespoitory.findAll(pageable);
    }

    public Page<CommentPostgres> getAllForRecord(Pageable pageable, UUID recordId){
        return commentRespoitory.findAllByRecordId(recordId, pageable);
    }
}
