package org.example.test.season;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.integration.util.TimeTestUtil;
import org.example.test.data.SeriesGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.lifecycle.Startables;

import static io.restassured.RestAssured.given;
import static org.example.test.data.PostPayloadGenerator.createSeasonPojo;
import static org.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "org.example.test")
public class TestSeasonLIfecyleIntegration extends TestContainers {

    @Autowired
    private SeriesGenerator seriesGenerator;

    @BeforeAll
    public static void beforeAll() {
        TestContainers.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        Integer port = MEDIA_MANAGEMENT_CONTAINER.getMappedPort(8080);

        registry.add("publicrest.port", () -> PUBLIC_REST_CONTAINER.getMappedPort(8080));
        registry.add("publicrest.host", () -> "localhost");
        registry.add("media.management.port", () -> port);
        registry.add("media.management.host", () -> "localhost");
    }

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:"+MEDIA_MANAGEMENT_CONTAINER.getMappedPort(8080);
    }

    @Test
    void testSeasonLifecycle() throws Exception {
        String seriesId =  seriesGenerator.generate().getId().toString();

        String body = MAPPER.writeValueAsString(createSeasonPojo("Season Title",1));

        // create series
        JsonPath jsonPath = given()
                .header("Content-type", "application/json")
                .body(body)
                .when().post("/series/{seriesId}/season",seriesId)
                .then()
                .statusCode(201)
                .and()
                .body("title", equalTo("Season Title"))
                .body("order", equalTo(1))
                .body("id", notNullValue())
                .extract()
                .body()
                .jsonPath();

        String seasonId = jsonPath.get("id");

        // get series
        Response getBody = given()
                .when().get("/series/{seriesId}/season/{seasonId}", seriesId,seasonId)
                .then()
                .statusCode(200)
                .and()
                .body("title", equalTo("Season Title"))
                .body("order", equalTo(1))
                .body("id", equalTo(seasonId))
                .extract()
                .response();

        assertTrue(TimeTestUtil.inLast5SecondsParse( getBody.jsonPath().get("creationTimestamp")),
                "creationTimestamp="+getBody.jsonPath().get("creationTimestamp"));
        assertTrue(TimeTestUtil.inLast5SecondsParse( getBody.jsonPath().get("lastUpdate")),
                "lastUpdate="+getBody.jsonPath().get("lastUpdate"));

        // delete series
        given()
                .when().delete("/series/{id}/season/{seasonId}", seriesId, seasonId)
                .then()
                .statusCode(204);

        given()
                .when().get("/series/{id}/season/{seasonId}", seriesId, seasonId)
                .then()
                .statusCode(404);
    }
}
