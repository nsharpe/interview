package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.core.model.AuthenticationInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.example.core.util.Util.OBJECT_MAPPER;

@Slf4j
@RequiredArgsConstructor
public class TokenProcessor extends OncePerRequestFilter {

    private final TokenRepo tokenRepo;

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

        AuthenticationInfo authenticationInfo = tokenRepo.infoForToken(jwt);

        if (authenticationInfo == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request, response);
            return;
        }

        if (authenticationInfo.getRoles() != null) {
            try {
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
        } else {
            log.atInfo()
                    .addKeyValue("userId", authenticationInfo::getUserId)
                    .log("User has no roles");
        }

        filterChain.doFilter(request, response);
    }
}
