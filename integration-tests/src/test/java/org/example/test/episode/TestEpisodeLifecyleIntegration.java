package org.example.test.episode;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.integration.util.TimeTestUtil;
import org.example.media.management.sdk.api.EpisodeControllerApi;
import org.example.media.management.sdk.models.EpisodeModel;
import org.example.media.management.sdk.models.SeasonModel;
import org.example.media.management.sdk.models.SeriesModel;
import org.example.test.data.AuthenticationGenerator;
import org.example.test.data.EpisodeGenerator;
import org.example.test.data.SeasonGenerator;
import org.example.test.data.SeriesGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "org.example.test")
public class TestEpisodeLifecyleIntegration extends TestContainers {

    @Autowired
    private SeasonGenerator seasonGenerator;

    @Autowired
    EpisodeGenerator episodeGenerator;

    @Autowired
    EpisodeControllerApi episodeControllerApi;

    @Autowired
    private AuthenticationGenerator authenticationGenerator;

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:"+getMediaManagmentPort();
    }

    @Test
    void testEpisodeLifeCycle() throws Exception {
        SeasonModel seasonModel = seasonGenerator.generate();

        String seriesId = seasonModel.getSeriesId().toString();
        String seasonId = seasonModel.getId().toString();

        String body = MAPPER.writeValueAsString(createEpisodePojo("Episode Title",1));

        // create series
        JsonPath jsonPath = given()
                .header("Content-type", "application/json")
                .header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .body(body)
                .when().post("/series/{seriesId}/season/{seasonId}/episode",seriesId, seasonId)
                .then()
                .statusCode(201)
                .and()
                .body("title", equalTo("Episode Title"))
                .body("order", equalTo(1))
                .body("id", notNullValue())
                .extract()
                .body()
                .jsonPath();

        String episode = jsonPath.get("id");

        // get series
        Response getBody = given()
                .header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .when().get("/series/{seriesId}/season/{seasonId}/episode/{episode}", seriesId,seasonId,episode)
                .then()
                .statusCode(200)
                .and()
                .body("title", equalTo("Episode Title"))
                .body("order", equalTo(1))
                .body("season", equalTo(seasonId))
                .body("series", equalTo(seriesId))
                .body("id", equalTo(episode))
                .extract()
                .response();

        assertTrue(TimeTestUtil.inLast5SecondsParse( getBody.jsonPath().get("creationTimestamp")),
                "creationTimestamp="+getBody.jsonPath().get("creationTimestamp"));
        assertTrue(TimeTestUtil.inLast5SecondsParse( getBody.jsonPath().get("lastUpdate")),
                "lastUpdate="+getBody.jsonPath().get("lastUpdate"));

        // delete series
        given().header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .when().delete("/series/{id}/season/{seasonId}/episode/{episode}", seriesId, seasonId, episode)
                .then()
                .statusCode(204);

        given().header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .when().get("/series/{id}/season/{seasonId}/episode/{episode}", seriesId, seasonId, episode)
                .then()
                .statusCode(404);
    }

    public static Map<String,Object> createEpisodePojo(String title, int order){
        Map<String,Object> pojo = new HashMap<>();

        pojo.put("title",title);
        pojo.put("order",order);
        pojo.put("length","PT1H");

        return pojo;
    }

    @Test
    void testGetAllEpisodesForSeason(){
        SeasonModel seasonModel = seasonGenerator.generate();

        EpisodeModel episode1 = episodeGenerator.generate(x->{
            x.setSeasonid(seasonModel.getId());
            x.setSeriesId(seasonModel.getSeriesId());
            x.getEpisodeCreateModel().setOrder(0);
            return x;
        });
        EpisodeModel episode2 = episodeGenerator.generate(x->{
            x.setSeasonid(seasonModel.getId());
            x.setSeriesId(seasonModel.getSeriesId());
            x.getEpisodeCreateModel().setOrder(2);
            return x;
        });

        assertNotNull(seasonModel);
        assertNotNull(seasonModel.getId());
        assertNotNull(seasonModel.getSeriesId());

        Set<UUID> episodes =  episodeControllerApi.getAllEpisodesForSeason(seasonModel.getId(),seasonModel.getSeriesId())
                .collect(Collectors.toSet())
                .block();

        assertNotNull(episodes);
        assertEquals(2,episodes.size());
        assertTrue(episodes.contains(episode1.getId()));
        assertTrue(episodes.contains(episode2.getId()));
    }
}
