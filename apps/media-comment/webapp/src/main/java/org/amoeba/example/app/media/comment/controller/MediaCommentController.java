package org.amoeba.example.app.media.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.amoeba.example.app.media.comment.controller.model.CommentPostResponse;
import org.amoeba.example.app.media.comment.controller.model.PaginatedCommentsResponse;
import org.amoeba.example.app.media.comment.controller.model.PostCommentRequest;
import org.amoeba.example.comment.CommentService;
import org.amoeba.example.comment.repository.CommentPostgres;
import org.amoeba.example.core.model.AuthenticationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
                                    schema = @Schema(implementation = CommentPostResponse.class)))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CommentPostResponse> postComment(@RequestBody Mono<PostCommentRequest> postComment,
                                                 @PathVariable("mediaId") UUID mediaID,
                                                 @AuthenticationPrincipal AuthenticationInfo authInfo) {

        return postComment.map(x -> CommentPostgres.builder()
                        .comment(x.getComment())
                        .mediaPositionMs(x.getMediaPositionMs())
                        .recordId(mediaID)
                        .recordType("media")
                        .userId(authInfo.getUserId())
                        .build())
                .map(commentService::createComment)
                .map(CommentPostResponse::of);
    }

    @Operation(summary = "Get paginated comments for a media record",
            responses = {
                    @ApiResponse(description = "Paginated list of comments",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
   	      		        schema = @Schema(implementation = PaginatedCommentsResponse.class)))})
    @GetMapping
    public Mono<PaginatedCommentsResponse> getComments(
            @PathVariable("mediaId") UUID mediaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdTimestamp") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<CommentPostgres> commentPage = commentService.getAllByRecordId(mediaId, "media", pageable);

        return Mono.just(PaginatedCommentsResponse.of(commentPage.getContent(), page, size, commentPage.getTotalElements()));
    }
}
