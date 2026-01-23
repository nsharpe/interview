package org.example.test.user;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.integration.util.TimeTestUtil;
import org.example.publicrest.sdk.api.UserControllerApi;
import org.example.publicrest.sdk.models.UserModel;
import org.example.test.data.AuthenticationGenerator;
import org.example.test.data.UserGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    private AuthenticationGenerator authenticationGenerator;

    @BeforeAll
    public static void beforeAll() {
        TestContainers.start();
    }

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        authenticationGenerator.resetBearerToken();
        RestAssured.baseURI = "http://localhost:" + PUBLIC_REST_CONTAINER.getMappedPort(8080);
    }

    @Test
    void testUserLifecycle() throws Exception {
        String body = MAPPER.writeValueAsString(createUserPojo());

        // create user
        JsonPath jsonPath = given()
                .header("Content-type", "application/json")
                .header("Authorization", authenticationGenerator.getAdminBearerHeader())
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
                .header("Authorization", authenticationGenerator.getAdminBearerHeader())
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
                .header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .when().delete("/user/{id}", id)
                .then()
                .statusCode(204);

        given()
                .header("Authorization", authenticationGenerator.getAdminBearerHeader())
                .when().get("/user/{id}", id)
                .then()
                .statusCode(404);
    }

    @Test
    void testGetWithMoreThanOneEntry()throws  Exception{
        userGenerator.generate();
        UserModel userModel = userGenerator.generate();

        assertNotNull(userModel);

        UserModel getUser = userControllerApi.getUser(userModel.getId())
                .block();

        assertNotNull(userModel);

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
