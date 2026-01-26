package org.example.security;

import org.example.core.model.AuthenticationInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled",havingValue = "true",matchIfMissing = true)
    public SecurityFilterChain filterChain(HttpSecurity http, TokenRepo tokenRepo) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api-docs/**", "/swagger-ui.html", "/swagger-ui/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new TokenProcessor(tokenRepo), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled",havingValue = "false")
    public SecurityFilterChain securityDisabledFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Allow all requests to any endpoint
                        .anyRequest().permitAll()
                ).build();
    }

    @Bean
    @Primary
    public RedisTemplate<String, AuthenticationInfo> authenticationTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, AuthenticationInfo> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(
                new Jackson2JsonRedisSerializer<>(AuthenticationInfo.class));

        return redisTemplate;
    }

    public static AuthenticationInfo getAuthenticationInfo() {
        return (AuthenticationInfo) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
