package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
public class TokenProcessor extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Logic: If ANY token is present, we trust it immediately
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String userId = authHeader.replace("Bearer ","");

            try{
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        UserInfo.builder()
                                .id(UUID.fromString(userId))
                                .build(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER")) // Authorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.atError()
                        .setCause(e)
                        .log("Could not set error context");
            }
        }

        filterChain.doFilter(request, response);
    }
}
