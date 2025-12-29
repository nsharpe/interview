package org.example.test.user;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import org.example.test.util.TestApplications;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.wait.strategy.DockerHealthcheckWaitStrategy;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.example.test.util.TestMapper.MAPPER;

public class TestUsersIntegration extends TestContainers {

    @BeforeAll
    public static void beforeAll() {
        MYSQL_CONTAINER.start();
         TestApplications.publicRest(MYSQL_CONTAINER.getMappedPort(3306))
                 .properties("")
                 .run();
    }

    @AfterAll
    public static void afterAll() {
        MYSQL_CONTAINER.stop();
    }

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void testUserLifecycle() throws Exception {
        String body = MAPPER.writeValueAsString(createUserPojo());

        given()
                .header("Content-type", "application/json")
                .body(body)
                .when().post("/user")
                .then()
                .statusCode(201);
    }

    private static Map<String,Object> createUserPojo(){
        Map<String,Object> userPojo = new HashMap<>();

        userPojo.put("firstName","John");
        userPojo.put("lastName","Smith");
        userPojo.put("email","john.smith@test.com");

        return userPojo;
    }
}
