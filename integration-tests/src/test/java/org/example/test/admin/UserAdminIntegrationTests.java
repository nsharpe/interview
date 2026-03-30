package org.example.test.admin;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import org.example.admin.sdk.api.UserAdminControllerApi;
import org.example.admin.sdk.models.UserModelPage;
import org.example.publicrest.sdk.models.UserModel;
import org.example.test.data.AuthenticationGenerator;
import org.example.test.data.UserGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "org.example.test")
public class UserAdminIntegrationTests extends TestContainers {

    @Autowired
    private UserGenerator userGenerator;

    @Autowired
    private AuthenticationGenerator authenticationGenerator;

    @Autowired
    private UserAdminControllerApi userAdminControllerApi;

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:"+getAdminPort();
    }

    @Test
    void testGetAllUser(){
        UserModel user1 = userGenerator.generate();
        UserModel user2 = userGenerator.generate();

        userAdminControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.getAdminBearerToken());

        UserModelPage allUsers = userAdminControllerApi.getUsers(0,1000,null)
                .block();

        assertNotNull(allUsers);

        assertNotNull(allUsers.getNumberOfElements());
        assertTrue(allUsers.getNumberOfElements() < 1000, "This test needs to be reworked, as there are now more then 1000 users in the database.  Because of this this test needs to be reworked.");

        assertNotNull(allUsers.getContent());
        Set<UUID> allIds = allUsers.getContent()
                .stream()
                .filter(Objects::nonNull)
                .map(org.example.admin.sdk.models.UserModel::getId)
                .collect(Collectors.toSet());

        assertTrue(allIds.contains(user1.getId()));
        assertTrue(allIds.contains(user2.getId()));
    }

    @Test
    void unauthorizedUsers(){
        UserModel userModel = userGenerator.generate();
        String subscriberToken = authenticationGenerator.generateTokenForSubscriber(userModel);

        given().header("Content-type", "application/json")
                .header("Authorization", "Bearer " + subscriberToken)
                .when()
                .get("/admin/user?page=1&size=1")
                .then()
                .statusCode(403);
    }
}
