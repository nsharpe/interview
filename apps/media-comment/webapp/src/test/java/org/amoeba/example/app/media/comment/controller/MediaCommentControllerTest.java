package org.amoeba.example.app.media.comment.controller;

import org.amoeba.example.app.media.comment.controller.model.CommentGet;
import org.amoeba.example.app.media.comment.controller.model.PaginatedCommentsResponse;
import org.amoeba.example.comment.CommentService;
import org.amoeba.example.comment.repository.CommentPostgres;
import org.amoeba.example.core.model.AuthenticationInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

@ExtendWith(MockitoExtension.class)
class MediaCommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private MediaCommentController mediaCommentController;

    @Test
    void testGetComments() {
        // Create mock data
        UUID mediaId = UUID.randomUUID();
        AuthenticationInfo authInfo = new AuthenticationInfo(UUID.randomUUID(), "testuser");

        CommentPostgres comment1 = CommentPostgres.builder()
                .comment("First comment")
                .mediaPositionMs(1000L)
                .recordId(mediaId)
                .recordType("media")
                .userId(authInfo.getUserId())
                .build();

        CommentPostgres comment2 = CommentPostgres.builder()
                .comment("Second comment")
                .mediaPositionMs(2000L)
                .recordId(mediaId)
                .recordType("media")
                .userId(authInfo.getUserId())
                .build();

        List<CommentPostgres> commentList = List.of(comment1, comment2);
        Page<CommentPostgres> page = new PageImpl<>(commentList);

        // Mock the service call
        when(commentService.getAllByRecordId(any(UUID.class), any(String.class), any(Pageable.class)))
                .thenReturn(page);

        // Test the endpoint
        WebTestClient webTestClient = WebTestClient.bindToController(mediaCommentController).build();

        webTestClient.get()
                .uri("/media/{mediaId}/comment?page=0&size=20", mediaId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PaginatedCommentsResponse.class);
    }
}