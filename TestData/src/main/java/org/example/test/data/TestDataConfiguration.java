package org.example.test.data;

import org.example.security.AuthenticationInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class TestDataConfiguration {

    @Bean
    @Qualifier(value = "testValueTemplate")
    public RedisTemplate<String, AuthenticationInfo> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, AuthenticationInfo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(
                new Jackson2JsonRedisSerializer<>(AuthenticationInfo.class));
        return redisTemplate;
    }
}
