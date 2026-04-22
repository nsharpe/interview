package org.amoeba.example.app.media.comment.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.amoeba.example.comment.repository.CommentPostgres;

import java.util.UUID;

import static org.amoeba.example.core.util.Util.OBJECT_MAPPER;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentGet {

    private String comment;
    private Long mediaPositionMs;
    private UUID userId;
    private String recordType;
    private UUID recordId;
    private Long createdTimestamp;

    public static CommentGet of(CommentPostgres source){
        CommentGet commentGet = OBJECT_MAPPER.convertValue(source, CommentGet.class);
        // Set the created timestamp from the embedded timeStamp
        if (source.getTimeStamp() != null) {
            commentGet.setCreatedTimestamp(source.getTimeStamp().getCreationTimestamp().toInstant().toEpochMilli());
        }
        return commentGet;
    }
}
