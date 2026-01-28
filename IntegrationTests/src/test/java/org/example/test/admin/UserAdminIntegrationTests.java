package org.example.test.admin;

import org.example.admin.sdk.api.UserAdminControllerApi;
import org.example.publicrest.sdk.models.UserModel;
import org.example.test.data.AuthenticationGenerator;
import org.example.test.data.UserGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Test
    void testGetAllUserIds(){
        UserModel user1 = userGenerator.generate();
        UserModel user2 = userGenerator.generate();

        userAdminControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.getAdminBearerToken());

        List<UUID> allIdsList = userAdminControllerApi.getUsers().collectList().block();
        assertNotNull(allIdsList);
        Set<UUID> allIds = new HashSet<>(allIdsList);

        assertTrue(allIds.contains(user1.getId()));
        assertTrue(allIds.contains(user2.getId()));
    }
}
