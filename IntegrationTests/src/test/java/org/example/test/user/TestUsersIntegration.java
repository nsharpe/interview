package org.example.test.user;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.integration.util.TimeTestUtil;
import org.example.publicrest.sdk.api.UserControllerApi;
import org.example.publicrest.sdk.models.UserModel;
import org.example.test.data.UserGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "org.example.test",excludeFilters = @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "org\\.example\\.media.*"
))
public class TestUsersIntegration extends TestContainers {

    @Autowired
    private UserGenerator userGenerator;

    @Autowired
    private UserControllerApi userControllerApi;

    @BeforeAll
    public static void beforeAll() {
        TestContainers.start();
    }

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:"+PUBLIC_REST_CONTAINER.getMappedPort(8080);
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        Integer port = PUBLIC_REST_CONTAINER.getMappedPort(8080);

        registry.add("media.management.host", () -> "localhost");
        registry.add("media.management.port", () -> MEDIA_MANAGEMENT_CONTAINER.getMappedPort(8080));
        registry.add("publicrest.host", () -> "localhost");
        registry.add("publicrest.port", () -> port);
    }

    @Test
    void testUserLifecycle() throws Exception {
        String body = MAPPER.writeValueAsString(createUserPojo());

        // create user
        JsonPath jsonPath = given()
                .header("Content-type", "application/json")
                .body(body)
                .when().post("/user")
                .then()
                .statusCode(201)
                .and()
                .body("firstName", equalTo("John")) // Verify specific fields in the response body
                .body("lastName", equalTo("Smith"))
                .body("email", equalTo("john.smith@test.com"))
                .body("id", notNullValue())
                .extract()
                .body()
                .jsonPath();

        String id = jsonPath.get("id");

        // get user
        Response getBody = given()
                .when().get("/user/{id}", id)
                .then()
                .statusCode(200)
                .and()
                .body("firstName", equalTo("John")) // Verify specific fields in the response body
                .body("lastName", equalTo("Smith"))
                .body("email", equalTo("john.smith@test.com"))
                .body("id", equalTo(id))
                .extract()
                .response();

        assertTrue(TimeTestUtil.inLast5SecondsParse( getBody.jsonPath().get("creationTimestamp")),
                "creationTimestamp="+getBody.jsonPath().get("creationTimestamp"));
        assertTrue(TimeTestUtil.inLast5SecondsParse( getBody.jsonPath().get("lastUpdate")),
                "lastUpdate="+getBody.jsonPath().get("lastUpdate"));

        // delete user
        given()
                .when().delete("/user/{id}", id)
                .then()
                .statusCode(204);

        given()
                .when().get("/user/{id}", id)
                .then()
                .statusCode(404);
    }

    @Test
    void testGetWithMoreThanOneEntry()throws  Exception{
        userGenerator.generate();
        UserModel userModel = userGenerator.generate();

        UserModel getUser = userControllerApi.getUser(userModel.getId());

        assertEquals(getUser.getId(),userModel.getId());
    }

    private static Map<String,Object> createUserPojo(){
        Map<String,Object> userPojo = new HashMap<>();

        userPojo.put("firstName","John");
        userPojo.put("lastName","Smith");
        userPojo.put("email","john.smith@test.com");

        return userPojo;
    }
}
