package org.example.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenRepo {

    private final RedisTemplate<String, AuthenticationInfo> redisTemplate;

    public AuthenticationInfo infoForToken(String jwt){
        return redisTemplate.opsForValue().get("Bearer "+jwt);
    }
}
