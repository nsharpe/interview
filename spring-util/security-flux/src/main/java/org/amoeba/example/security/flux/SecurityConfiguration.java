package org.amoeba.example.security.flux;

import org.amoeba.example.core.model.AuthenticationInfo;
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
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class SecurityConfiguration {

     @Value(value = "${application.role:ANY}")
    private String role;

     @Bean
     @ConditionalOnProperty(value = "spring.security.enabled",havingValue = "true",matchIfMissing = true)
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, TokenRepo tokenRepo) {
        return http
                 .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                 .csrf(ServerHttpSecurity.CsrfSpec::disable)
                 .authorizeExchange(exchanges -> {
                    var match = exchanges.pathMatchers("/api-docs/**",
                                     "/swagger-ui.html",
                                     "/swagger-ui/**",
                                     "/actuator",
                                     "/actuator/**")
                             .permitAll();

                    if ("ANY".equals(role)) {
                        match.anyExchange().permitAll();
                     } else {
                        match.anyExchange().hasRole(role);
                     }
                 })
                 .addFilterAt(new TokenProcessor(tokenRepo), SecurityWebFiltersOrder.AUTHENTICATION)
                 .build();
     }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // The React App
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
     }

     @Bean
     @ConditionalOnProperty(value = "spring.security.enabled",havingValue = "false")
    public SecurityWebFilterChain securityDisabledFilterChain(ServerHttpSecurity http) throws Exception {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                 .authorizeExchange(auth -> auth
                         .anyExchange()
                         .permitAll()
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

    public static Mono<AuthenticationInfo> getAuthenticationInfo() {
        return ReactiveSecurityContextHolder.getContext()
                 .map(SecurityContext::getAuthentication)
                 .map(x->(AuthenticationInfo)x.getPrincipal());
     }
}
