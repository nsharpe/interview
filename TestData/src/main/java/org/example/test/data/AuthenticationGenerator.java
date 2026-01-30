package org.example.test.data;

import org.example.admin.sdk.api.UserAdminControllerApi;
import org.example.admin.sdk.models.AdminAuthorization;
import org.example.core.model.AuthenticationInfo;
import org.example.publicrest.sdk.models.UserModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@ConditionalOnProperty(value = "spring.redis.enabled", havingValue = "true", matchIfMissing = true)
public class AuthenticationGenerator {

    private final static String ADMIN_AUTH_TOKEN = "123";

    private final RedisTemplate<String, AuthenticationInfo> redisTemplate;
    private final UserAdminControllerApi userAdminControllerApi;

    public AuthenticationGenerator(
            @Qualifier("testValueTemplate") RedisTemplate<String, AuthenticationInfo> redisTemplate,
            UserAdminControllerApi userAdminControllerApi) {
        this.userAdminControllerApi = userAdminControllerApi;
        this.redisTemplate = redisTemplate;
        resetBearerToken();
    }

    public void resetBearerToken(){
        redisTemplate.opsForValue().set(
                "Bearer " + ADMIN_AUTH_TOKEN,
                AuthenticationInfo.builder()
                        .userId(UUID.randomUUID())
                        .roles(List.of("ADMIN"))
                        .build(),
                Duration.ofDays(365)
        );
    }

    public String getAdminBearerToken(){
        return ADMIN_AUTH_TOKEN;
    }

    public String getAdminBearerHeader(){
        return "Bearer "+ ADMIN_AUTH_TOKEN;
    }

    public String generateTokenForSubscriber(UserModel userModel){
        return generateTokenForSubscriber(userModel.getId());
    }

    public String generateTokenForSubscriber(UUID userId){
        userAdminControllerApi.getApiClient()
                .setBearerToken(getAdminBearerToken());
        return userAdminControllerApi.loginAsUser(userId)
                .mapNotNull(AdminAuthorization::getToken)
                .block();
    }
}
