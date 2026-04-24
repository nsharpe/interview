package org.amoeba.example.app.media.comment.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.amoeba.example.util.webflux.OpenApiPage;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentGetPage extends OpenApiPage {
    @Schema(description = "The list of comments")
    private List<CommentGet> content;
}