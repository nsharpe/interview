package org.amoeba.example.app.media.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.amoeba.example.app.media.comment.controller.model.CommentResponse;
import org.amoeba.example.app.media.comment.controller.model.PostComment;
import org.amoeba.example.comment.CommentService;
import org.amoeba.example.comment.repository.CommentPostgres;
import org.amoeba.example.security.SecurityConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/media/{mediaId}/comment")
@RequiredArgsConstructor
public class MediaCommentController {

    private final CommentService commentService;

    @Operation(summary = "Create a comment",
            responses = {
                    @ApiResponse(description = "Information to retrieve the comment",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse.class)))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CommentResponse> postComment(@Valid @RequestBody PostComment postComment,
                                             @PathVariable("mediaId") UUID mediaID){
        CommentPostgres postgres = CommentPostgres.builder()
                .comment(postComment.getComment())
                .mediaPositionMs(postComment.getMediaPositionMs())
                .recordId(mediaID)
                .recordType("media")
                .userId(SecurityConfiguration.getAuthenticationInfo().getUserId())
                .build();

        return Mono.justOrEmpty(CommentResponse.of(commentService.createComment(postgres)));
    }
}
