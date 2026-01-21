package org.example.test.episode;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.integration.util.TimeTestUtil;
import org.example.media.management.sdk.models.SeasonModel;
import org.example.test.data.SeasonGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "org.example.test")
public class TestEpisodeLifecyleIntegration extends TestContainers {

    @Autowired
    private SeasonGenerator seasonGenerator;

    @BeforeAll
    public static void beforeAll() {
        TestContainers.start();
    }

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:"+MEDIA_MANAGEMENT_CONTAINER.getMappedPort(8080);
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
        given()
                .when().delete("/series/{id}/season/{seasonId}/episode/{episode}", seriesId, seasonId, episode)
                .then()
                .statusCode(204);

        given()
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
}
