package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.core.model.AuthenticationInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "spring.security.enabled",havingValue = "true",matchIfMissing = true)
public class TokenRepo {

    private final RedisTemplate<String, AuthenticationInfo> redisTemplate;

    public AuthenticationInfo infoForToken(String jwt){
        return redisTemplate.opsForValue().get("Bearer "+jwt);
    }
}
