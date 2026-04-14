package org.amoeba.example.app.media.comment.controller.model;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Data;
import org.amoeba.example.comment.repository.CommentPostgres;

import static org.amoeba.example.core.util.Util.OBJECT_MAPPER;

@Data
public class CommentData {

    private String comment;
    private Long mediaPositionMs;


    public static CommentData of(CommentPostgres source){
        return OBJECT_MAPPER.convertValue(source, CommentData.class);
    }
}
