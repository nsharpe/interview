package org.amoeba.example.app.media.comment.controller.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.amoeba.example.comment.repository.CommentPostgres;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.amoeba.example.core.util.Util.OBJECT_MAPPER;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentGet {

    @Getter(AccessLevel.NONE)
    private UUID publicId;
    private String comment;
    private Long mediaPositionMs;
    private UUID userId;
    private String recordType;
    private UUID recordId;
    @JsonAlias("creationTimestamp")
    private OffsetDateTime created;

    public UUID getId(){
        return publicId;
    }

    public static CommentGet of(CommentPostgres source){
        return OBJECT_MAPPER.convertValue(source, CommentGet.class);
    }
}
