package org.amoeba.example.test.media.comment;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import org.amoeba.example.app.media.comment.sdk.api.CommentControllerApi;
import org.amoeba.example.app.media.comment.sdk.api.MediaCommentControllerApi;
import org.amoeba.example.app.media.comment.sdk.models.CommentGet;
import org.amoeba.example.app.media.comment.sdk.models.CommentPostResponse;
import org.amoeba.example.app.media.comment.sdk.models.PostCommentRequest;
import org.amoeba.example.apps.user_management.sdk.models.UserModel;
import org.amoeba.example.media.management.sdk.models.EpisodeModel;
import org.amoeba.example.media.metric.sdk.api.MediaPerformanceControllerApi;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "org.amoeba.example.test")
public class MediaCommentControllerIntegrationTest extends TestContainers {

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
    public void testCreateComment(){
        UserModel userModel = userGenerator.generate();
        EpisodeModel episodeModel = episodeGenerator.generate();

        mediaCommentControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.generateTokenForSubscriber(userModel));

        PostCommentRequest request = new PostCommentRequest()
                .comment("Test Comment")
                        .mediaPositionMs(10L);

        CommentPostResponse response = mediaCommentControllerApi.postComment(episodeModel.getId(), request)
                .block(Duration.ofSeconds(10));

        assertNotNull(response);
        assertNotNull(response.getId());

        CommentGet getResponse = commentControllerApi.getComment(response.getId()).block();

        assertNotNull(getResponse);

        assertEquals(Long.valueOf(10L), getResponse.getMediaPositionMs());
        assertEquals("Test Comment", getResponse.getComment());
        assertEquals(userModel.getId(), getResponse.getUserId());
    }
}
