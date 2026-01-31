package org.example.test.series;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.integration.util.TimeTestUtil;
import org.example.media.management.sdk.api.EpisodeControllerApi;
import org.example.media.management.sdk.api.SeasonControllerApi;
import org.example.media.management.sdk.api.SeriesControllerApi;
import org.example.media.management.sdk.models.EpisodeModel;
import org.example.media.management.sdk.models.SeasonModel;
import org.example.media.management.sdk.models.SeriesModel;
import org.example.media.management.sdk.models.SeriesPage;
import org.example.test.data.AuthenticationGenerator;
import org.example.test.data.EpisodeGenerator;
import org.example.test.data.SeasonGenerator;
import org.example.test.data.SeriesGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.example.test.data.PostPayloadGenerator.createSeriesPojo;
import static org.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestSeriesLifecyleIntegration extends TestContainers {
    @Autowired
    AuthenticationGenerator authenticationGenerator;

    @Autowired
    SeriesControllerApi seriesControllerApi;

    @Autowired
    SeriesGenerator seriesGenerator;

    @Autowired
    EpisodeGenerator episodeGenerator;

    @Autowired
    SeasonGenerator seasonGenerator;
    @Autowired
    private EpisodeControllerApi episodeControllerApi;
    @Autowired
    private SeasonControllerApi seasonControllerApi;

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:"+getMediaManagmentPort();
    }

    @Test
    void testseriesLifecycle() throws Exception {
        String body = MAPPER.writeValueAsString(createSeriesPojo("Series Title"));

        // create series
        JsonPath jsonPath = given().header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .header("Content-type", "application/json")
                .body(body)
                .when().post("/series")
                .then()
                .statusCode(201)
                .and()
                .body("title", equalTo("Series Title"))
                .body("description", equalTo("A rousing story"))
                .body("locale", equalTo("en"))
                .body("id", notNullValue())
                .extract()
                .body()
                .jsonPath();

        String id = jsonPath.get("id");

        // get series
        Response getBody = given()
                .header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .when().get("/series/{id}", id)
                .then()
                .statusCode(200)
                .and()
                .body("title", equalTo("Series Title"))
                .body("description", equalTo("A rousing story"))
                .body("locale", equalTo("en"))
                .body("id", equalTo(id))
                .extract()
                .response();

        assertTrue(TimeTestUtil.inLast5SecondsParse( getBody.jsonPath().get("creationTimestamp")),
                "creationTimestamp="+getBody.jsonPath().get("creationTimestamp"));
        assertTrue(TimeTestUtil.inLast5SecondsParse( getBody.jsonPath().get("lastUpdate")),
                "lastUpdate="+getBody.jsonPath().get("lastUpdate"));

        // delete series
        given().header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .when().delete("/series/{id}", id)
                .then()
                .statusCode(204);

        given().header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .when().get("/series/{id}", id)
                .then()
                .statusCode(404);
    }


    @Test
    void testGetAllSeriesId(){
        SeriesModel series1 = seriesGenerator.generate();
        SeriesModel series2 = seriesGenerator.generate();

        seriesControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.getAdminBearerToken());
        Set<UUID> series =  seriesControllerApi.getAll(0, 1000, null)
                        .mapNotNull(SeriesPage::getContent)
                .mapNotNull(x->x.stream()
                        .map(SeriesModel::getId)
                        .collect(Collectors.toSet()))
                        .block();

        assertNotNull(series);

        assertTrue(series.size() <= 999, "There are now over 1k series in the test suite.  You will have to rework (rethink) this test, series.size="+series.size());

        assertTrue(series.contains(series1.getId()));
        assertTrue(series.contains(series2.getId()));
    }



    @Test
    public void getFirstEpisodeOneSeason() {
        UUID seriesId = seriesGenerator.generate().getId();
        assertNotNull(seriesId);

        SeasonModel firstSeasonModel = seasonGenerator.generate(x -> x.toBuilder()
                .seriesId(seriesId)
                .seasonCreateModel(x.seasonCreateModel().order(0))
                .build());

        EpisodeModel firstSeasonEpisode1 = episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(firstSeasonModel.getId())
                        .episodeCreateModel(x.getEpisodeCreateModel().order(0))
                        .build()
        );

        episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(firstSeasonModel.getId())
                        .episodeCreateModel(x.getEpisodeCreateModel().order(1))
                        .build()
        );

        seriesControllerApi.getApiClient().setBearerToken(authenticationGenerator.getAdminBearerToken());

        UUID episodeId = seriesControllerApi.getFirstEpisodeId(seriesId).blockFirst();
        assertNotNull(episodeId);
        assertEquals(firstSeasonEpisode1.getId(),episodeId);

        episodeId = seriesControllerApi.getFirstEpisodeId(seriesId).blockLast();
        assertNotNull(episodeId);
        assertEquals(firstSeasonEpisode1.getId(),episodeId);
    }

    @Test
    public void getFirstEpisodeTwoSeason() {
        UUID seriesId = seriesGenerator.generate().getId();
        assertNotNull(seriesId);

        UUID firstSeasonId = seasonGenerator.generate(x -> x.toBuilder()
                .seriesId(seriesId)
                .seasonCreateModel(x.seasonCreateModel().order(0))
                .build())
                .getId();

        UUID secondSeasonId = seasonGenerator.generate(x -> x.toBuilder()
                        .seriesId(seriesId)
                        .seasonCreateModel(x.seasonCreateModel().order(1))
                        .build())
                .getId();

        EpisodeModel firstSeasonEpisode1 = episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(firstSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(0))
                        .build()
        );

        episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(firstSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(1))
                        .build()
        );

        episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(secondSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(0))
                        .build()
        );

        episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(secondSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(1))
                        .build()
        );

        seriesControllerApi.getApiClient().setBearerToken(authenticationGenerator.getAdminBearerToken());

        UUID episodeId = seriesControllerApi.getFirstEpisodeId(seriesId).blockFirst();
        assertNotNull(episodeId);
        assertEquals(firstSeasonEpisode1.getId(),episodeId);

        episodeId = seriesControllerApi.getFirstEpisodeId(seriesId).blockLast();
        assertNotNull(episodeId);
        assertEquals(firstSeasonEpisode1.getId(),episodeId);
    }

    @Test
    public void getFirstEpisodeTwoSeason_firstEpisodeDeleted() {
        UUID seriesId = seriesGenerator.generate().getId();
        assertNotNull(seriesId);

        UUID firstSeasonId = seasonGenerator.generate(x -> x.toBuilder()
                        .seriesId(seriesId)
                        .seasonCreateModel(x.seasonCreateModel().order(0))
                        .build())
                .getId();

        assertNotNull(firstSeasonId);

        UUID secondSeasonId = seasonGenerator.generate(x -> x.toBuilder()
                        .seriesId(seriesId)
                        .seasonCreateModel(x.seasonCreateModel().order(1))
                        .build())
                .getId();

        EpisodeModel firstSeasonEpisode1 = episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(firstSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(0))
                        .build()
        );

        assertNotNull(firstSeasonEpisode1);
        assertNotNull(firstSeasonEpisode1.getId());

        episodeControllerApi.delete(firstSeasonEpisode1.getId(),firstSeasonId,seriesId)
                .block();

        EpisodeModel firstSeasonEpisode2 = episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(firstSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(1))
                        .build()
        );

        episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(secondSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(0))
                        .build()
        );

        episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(secondSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(1))
                        .build()
        );

        seriesControllerApi.getApiClient().setBearerToken(authenticationGenerator.getAdminBearerToken());

        UUID episodeId = seriesControllerApi.getFirstEpisodeId(seriesId).blockFirst();
        assertNotNull(episodeId);
        assertEquals(firstSeasonEpisode2.getId(),episodeId);

        episodeId = seriesControllerApi.getFirstEpisodeId(seriesId).blockLast();
        assertNotNull(episodeId);
        assertEquals(firstSeasonEpisode2.getId(),episodeId);
    }


    @Test
    public void getFirstEpisodeTwoSeason_firstSeasonDeleted() {
        UUID seriesId = seriesGenerator.generate().getId();
        assertNotNull(seriesId);

        UUID firstSeasonId = seasonGenerator.generate(x -> x.toBuilder()
                        .seriesId(seriesId)
                        .seasonCreateModel(x.seasonCreateModel().order(0))
                        .build())
                .getId();

        assertNotNull(firstSeasonId);

        UUID secondSeasonId = seasonGenerator.generate(x -> x.toBuilder()
                        .seriesId(seriesId)
                        .seasonCreateModel(x.seasonCreateModel().order(1))
                        .build())
                .getId();

        EpisodeModel firstSeasonEpisode1 = episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(firstSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(0))
                        .build()
        );

        assertNotNull(firstSeasonEpisode1);
        assertNotNull(firstSeasonEpisode1.getId());

        EpisodeModel firstSeasonEpisode2 = episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(firstSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(1))
                        .build()
        );

        EpisodeModel secondSeasonEpisode1 = episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(secondSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(0))
                        .build()
        );

        episodeGenerator.generate(x ->
                x.toBuilder()
                        .seriesId(seriesId)
                        .seasonid(secondSeasonId)
                        .episodeCreateModel(x.getEpisodeCreateModel().order(1))
                        .build()
        );

        seasonControllerApi.getApiClient().setBearerToken(authenticationGenerator.getAdminBearerToken());
        seasonControllerApi.delete1(firstSeasonId,seriesId).block();

        seriesControllerApi.getApiClient().setBearerToken(authenticationGenerator.getAdminBearerToken());

        UUID episodeId = seriesControllerApi.getFirstEpisodeId(seriesId).blockFirst();
        assertNotNull(episodeId);
        assertEquals(secondSeasonEpisode1.getId(),episodeId);

        episodeId = seriesControllerApi.getFirstEpisodeId(seriesId).blockLast();
        assertNotNull(episodeId);
        assertEquals(secondSeasonEpisode1.getId(),episodeId);
    }
}
