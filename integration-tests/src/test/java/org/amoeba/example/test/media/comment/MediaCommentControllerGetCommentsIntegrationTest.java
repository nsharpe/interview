package org.amoeba.example.test.media.comment;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.amoeba.example.app.media.comment.sdk.api.CommentControllerApi;
import org.amoeba.example.app.media.comment.sdk.api.MediaCommentControllerApi;
import org.amoeba.example.app.media.comment.sdk.models.CommentGet;
import org.amoeba.example.app.media.comment.sdk.models.CommentGetPage;
import org.amoeba.example.app.media.comment.sdk.models.CommentPostResponse;
import org.amoeba.example.app.media.comment.sdk.models.PostCommentRequest;
import org.amoeba.example.apps.user_management.sdk.models.UserModel;
import org.amoeba.example.media.management.sdk.models.EpisodeModel;
import org.amoeba.example.test.data.AuthenticationGenerator;
import org.amoeba.example.test.data.EpisodeGenerator;
import org.amoeba.example.test.data.UserGenerator;
import org.amoeba.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "org.amoeba.example.test")
public class MediaCommentControllerGetCommentsIntegrationTest extends TestContainers {

    @Autowired
    private MediaCommentControllerApi mediaCommentControllerApi;

    @Autowired
    private CommentControllerApi commentControllerApi;

    @Autowired
    private EpisodeGenerator episodeGenerator;

    @Autowired
    private UserGenerator userGenerator;

    @Autowired
    private AuthenticationGenerator authenticationGenerator;

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        mediaCommentControllerApi
                .getApiClient()
                .setBasePath("http://localhost:"+getMediaCommentPort());
        commentControllerApi
                .getApiClient()
                .setBasePath("http://localhost:"+getMediaCommentPort());
    }

    @Test
    public void testGetCommentsForMediaWithPagination() {
        // Generate a user and episode for testing
        UserModel userModel = userGenerator.generate();
        EpisodeModel episodeModel = episodeGenerator.generate();

        mediaCommentControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.generateTokenForSubscriber(userModel));

        // Create multiple comments for the same media
        int commentCount = 5;

        for (int i = 0; i < commentCount; i++) {
            PostCommentRequest request = new PostCommentRequest()
                    .comment("Test Comment " + i)
                    .mediaPositionMs((long) (i * 1000));

            CommentPostResponse response = mediaCommentControllerApi.postComment(episodeModel.getId(), request)
                    .block(Duration.ofSeconds(10));

            assertNotNull(response);
            assertNotNull(response.getId());
        }

        // Test getting comments with pagination
        CommentGetPage response = mediaCommentControllerApi.getCommentsForMedia(
                episodeModel.getId(),
                        0,
                        3)
                .block(Duration.ofSeconds(10));

        assertNotNull(response);
        assertEquals(5, response.getTotalElements());
        assertEquals(2, response.getTotalPages());
        assertEquals(0, response.getNumber());
        assertEquals(3, response.getSize());

        // Verify the comments returned are correct
        List<CommentGet> content = response.getContent();
        for (int i = 0; i < content.size(); i++) {
            CommentGet comment = content.get(i);
            assertNotNull(comment.getId());
            assertEquals("Test Comment " + i, comment.getComment());
            assertEquals((long) (i * 1000), comment.getMediaPositionMs());
            assertEquals(userModel.getId(), comment.getUserId());
        }
    }

    @Test
    public void testGetCommentsForMediaEmpty() {
        // Generate a user and episode for testing
        UserModel userModel = userGenerator.generate();
        EpisodeModel episodeModel = episodeGenerator.generate();

        mediaCommentControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.generateTokenForSubscriber(userModel));

        CommentGetPage response = mediaCommentControllerApi.getCommentsForMedia(
                episodeModel.getId(),
                        0,
                        10)
                .block(Duration.ofSeconds(10));

        assertNotNull(response);
        assertNotNull(response.getContent());
        assertEquals(0, response.getContent().size());
        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
    }

    @Test
    public void testGetCommentsForMediaWithDifferentPage() {
        // Generate a user and episode for testing
        UserModel userModel = userGenerator.generate();
        EpisodeModel episodeModel = episodeGenerator.generate();

        mediaCommentControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.generateTokenForSubscriber(userModel));

        // Create multiple comments for the same media
        int commentCount = 4;
        for (int i = 0; i < commentCount; i++) {
            PostCommentRequest request = new PostCommentRequest()
                    .comment("Test Comment " + i)
                    .mediaPositionMs((long) (i * 1000));

            mediaCommentControllerApi.postComment(episodeModel.getId(), request)
                    .block(Duration.ofSeconds(10));
        }

        // Test getting second page
        CommentGetPage response = mediaCommentControllerApi.getCommentsForMedia(
                episodeModel.getId(),
                        1,
                        2)
                .block(Duration.ofSeconds(10));

        assertNotNull(response);
        assertNotNull(response.getContent());
        assertEquals(2, response.getContent().size());
        assertEquals(4, response.getTotalElements());
        assertEquals(2, response.getTotalPages());
        assertEquals(1, response.getNumber());
        assertEquals(2, response.getSize());

        // Verify the comments returned are correct (should be items 2 and 3)
        List<CommentGet> content = response.getContent();
        assertEquals("Test Comment 2", content.get(0).getComment());
        assertEquals("Test Comment 3", content.get(1).getComment());
    }
}