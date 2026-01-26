package org.example.test.data;

import org.example.core.model.AuthenticationInfo;
import org.example.publicrest.sdk.models.UserModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationGenerator {

    private final static String ADMIN_AUTH_TOKEN = "123456890";

    private final RedisTemplate<String, AuthenticationInfo> redisTemplate;

    public AuthenticationGenerator(
            @Qualifier("testValueTemplate") RedisTemplate<String, AuthenticationInfo> redisTemplate) {
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
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(
                "Bearer " + token,
                AuthenticationInfo.builder()
                        .userId(userId)
                        .roles(List.of("SUBSCRIBER"))
                        .build(),
                Duration.ofDays(365)
        );

        return token;
    }

    public String generateToken(UUID userId, AuthenticationInfo.AuthenticationInfoBuilder authenticationInfoBuilder){
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(
                "Bearer " + token,
                authenticationInfoBuilder
                        .userId(userId)
                        .build(),
                Duration.ofDays(365)
        );

        return token;
    }
}
