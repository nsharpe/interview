package org.amoeba.example.test.media;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.amoeba.example.apps.user_management.sdk.models.UserModel;
import org.amoeba.example.media.management.sdk.models.EpisodeModel;
import org.amoeba.example.media.metric.sdk.api.MediaPerformanceControllerApi;
import org.amoeba.example.media.metric.sdk.models.MediaMetricModel;

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
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.amoeba.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "org.amoeba.example.test")
public class MediaPlayerControllerIntegrationTest extends TestContainers {

    @Autowired
    private EpisodeGenerator episodeGenerator;

    @Autowired
    private UserGenerator userGenerator;

    @Autowired
    private AuthenticationGenerator authenticationGenerator;

    private MediaPerformanceControllerApi mediaPerformanceControllerApi;

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:"+getMediaPlayPort();
        mediaPerformanceControllerApi = new MediaPerformanceControllerApi(getMediaMetricsApiClient());
        mediaPerformanceControllerApi.getApiClient().setBearerToken(authenticationGenerator.getAdminBearerToken());
    }

    @Test
    void testPlayerStartAccept() throws Exception {

        UserModel user = userGenerator.generate();
        String authToken = authenticationGenerator.generateTokenForSubscriber(user);
        EpisodeModel episodeModel = episodeGenerator.generate();
        assertNotNull(episodeModel.getLength());
        Duration episodeDuration = Duration.parse(episodeModel.getLength());
        UUID actionID = UUID.randomUUID();

        String body = MAPPER.writeValueAsString(
                Map.of("eventState",
                        Map.of("eventId", actionID,
                                "mediaPosition", 0,
                                "timestamp", "2026-01-23T01:01:01.000000001Z")
                ));

        JsonPath jsonPath = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(body)
                .when().post("/player/media/{mediaId}/start",episodeModel.getId())
                .then()
                .statusCode(202)
                .and()
                .body("actionId", notNullValue())
                .extract()
                .body()
                .jsonPath();

        String actionId = jsonPath.get("actionId");
        assertNotNull(UUID.fromString(actionId));


        body = MAPPER.writeValueAsString(
                Map.of("eventState",
                        Map.of("eventId", UUID.randomUUID(),
                                "mediaPosition", episodeDuration.toMillis(),
                                "timestamp", "2026-01-23T05:01:01.000000001Z",
                                "lastActionId",actionID),
                        "lastActionId",actionId
                ));

        jsonPath = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(body)
                .when().post("/player/media/stop")
                .then()
                .statusCode(202)
                .and()
                .body("actionId", notNullValue())
                .extract()
                .body()
                .jsonPath();

        actionId = jsonPath.get("actionId");
        assertNotNull(UUID.fromString(actionId));

        assertNotNull(episodeModel.getId());

        await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(50))
                .until(() -> null != mediaPerformanceControllerApi.getMediaViewTime(episodeModel.getId()).block());

        MediaMetricModel result = mediaPerformanceControllerApi.getMediaViewTime(episodeModel.getId()).block();

        assertNotNull(result);
        assertEquals( episodeDuration.toMillis(), result.getTotalPlayTimeMillis());

        assertEquals( 1, result.getTotalPlays() );
    }
}
