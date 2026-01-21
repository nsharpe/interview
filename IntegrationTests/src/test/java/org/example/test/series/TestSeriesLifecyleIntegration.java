package org.example.test.series;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.integration.util.TimeTestUtil;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.example.test.data.PostPayloadGenerator.createSeriesPojo;
import static org.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSeriesLifecyleIntegration extends TestContainers {

    @BeforeAll
    public static void beforeAll() {
        start();
    }

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:"+MEDIA_MANAGEMENT_CONTAINER.getMappedPort(8080);
    }

    @Test
    void testseriesLifecycle() throws Exception {
        String body = MAPPER.writeValueAsString(createSeriesPojo("Series Title"));

        // create series
        JsonPath jsonPath = given()
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
        given()
                .when().delete("/series/{id}", id)
                .then()
                .statusCode(204);

        given()
                .when().get("/series/{id}", id)
                .then()
                .statusCode(404);
    }
}
