package org.amoeba.example.app.media.comment.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostComment {

    private String comment;
    private Long mediaPositionMs;
}
