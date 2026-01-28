package org.example.test.series;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.integration.util.TimeTestUtil;
import org.example.media.management.sdk.api.SeriesControllerApi;
import org.example.media.management.sdk.models.SeriesModel;
import org.example.test.data.AuthenticationGenerator;
import org.example.test.data.SeriesGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.example.test.data.PostPayloadGenerator.createSeriesPojo;
import static org.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestSeriesLifecyleIntegration extends TestContainers {
    @Autowired
    AuthenticationGenerator authenticationGenerator;

    @Autowired
    SeriesControllerApi seriesControllerApi;

    @Autowired
    SeriesGenerator seriesGenerator;

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

        Set<UUID> series = seriesControllerApi.getAll()
                .collect(Collectors.toSet())
                .block();

        assertNotNull(series);
        assertTrue(series.contains(series1.getId()));
        assertTrue(series.contains(series2.getId()));
    }
}
