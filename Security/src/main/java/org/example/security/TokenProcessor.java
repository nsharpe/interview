package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class TokenProcessor extends OncePerRequestFilter {

    private final RedisTemplate<String, AuthenticationInfo> redisTemplate;

    public TokenProcessor(RedisTemplate<String, AuthenticationInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        AuthenticationInfo authenticationInfo = redisTemplate.opsForValue().get(jwt);

        if(authenticationInfo == null){
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("userInfo.roles "+ authenticationInfo.getRoles());

        if ( authenticationInfo.getRoles() != null) {
            try{
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        authenticationInfo,
                        null,
                        authenticationInfo.getRoles()
                                .stream()
                                .map(x -> new SimpleGrantedAuthority("ROLE_" + x))
                                .toList()
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
