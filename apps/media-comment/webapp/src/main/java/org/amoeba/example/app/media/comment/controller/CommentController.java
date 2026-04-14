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
import org.amoeba.example.core.exceptions.NotFoundException;
import org.amoeba.example.security.SecurityConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Get a specific comment",
            responses = {
                    @ApiResponse(description = "Information to retrieve the comment",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse.class)))})
    @GetMapping("/{id}")
    public Mono<CommentResponse> postComment(@PathVariable("id") UUID commentId){

        return Mono.just(commentService.get(commentId))
                .flatMap(x->{
                    if(x.isEmpty()){
                        return Mono.error(() -> new NotFoundException("comment", commentId));
                    }
                    return Mono.justOrEmpty(x);
                })
                .map(CommentResponse::of);
    }
}
