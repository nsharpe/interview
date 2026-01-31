package org.example.security;

import org.example.core.model.AuthenticationInfo;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Value(value = "${application.role:ANY}")
    private String role;

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled",havingValue = "true",matchIfMissing = true)
    public SecurityFilterChain filterChain(HttpSecurity http, TokenRepo tokenRepo) throws Exception {
        http.cors(cors->cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> {
                            var reqMatcher = auth.requestMatchers("/api-docs/**",
                                            "/swagger-ui.html",
                                            "/swagger-ui/**")
                                    .permitAll();

                            if ("ANY".equals(role)) {
                                reqMatcher.anyRequest().permitAll();
                            } else {
                                reqMatcher.anyRequest()
                                        .hasRole(role);
                            }
                        }
                )
                .addFilterBefore(new TokenProcessor(tokenRepo), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Your React App
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
    @ConditionalOnProperty(value = "spring.security.enabled",havingValue = "true", matchIfMissing = true)
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
