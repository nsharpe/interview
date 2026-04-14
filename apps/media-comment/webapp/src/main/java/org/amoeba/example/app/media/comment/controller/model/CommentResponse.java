package org.amoeba.example.app.media.comment.controller.model;

import lombok.Builder;
import lombok.Data;
import org.amoeba.example.comment.repository.CommentPostgres;

import java.util.UUID;

@Data
@Builder
public class CommentResponse {

    private UUID id;

    public static CommentResponse of(CommentPostgres databaseRecord){
        return CommentResponse.builder()
                .id(databaseRecord.getPublicId())
                .build();
    }

}
