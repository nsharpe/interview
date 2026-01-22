package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Usually disabled for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // All endpoints still "require" auth
                )
                // Add our "Auto-Approve" filter here
                .addFilterBefore(new TokenProcessor(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
