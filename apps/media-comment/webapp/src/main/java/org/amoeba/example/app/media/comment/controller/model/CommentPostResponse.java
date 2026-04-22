package org.amoeba.example.app.media.comment.controller.model;

import lombok.Builder;
import lombok.Data;
import org.amoeba.example.comment.repository.CommentPostgres;

import java.util.UUID;

@Data
@Builder
public class CommentPostResponse {

    private UUID id;

    public static CommentPostResponse of(CommentPostgres databaseRecord){
        return CommentPostResponse.builder()
                .id(databaseRecord.getPublicId())
                .build();
    }

}
