package org.amoeba.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.amoeba.example.core.model.AuthenticationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenProcessorTest {

    @Mock
    private TokenRepo tokenRepo;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private TokenProcessor tokenProcessor;

    @BeforeEach
    void setUp() {
        tokenProcessor = new TokenProcessor(tokenRepo);
        // Clear security context before each test
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_WithInvalidAuthorizationHeader() throws Exception {
        // Setup
        when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

        // Execute
        tokenProcessor.doFilterInternal(request, response, filterChain);

        // Verify
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(tokenRepo);
    }

    @Test
    void testDoFilterInternal_WithNullAuthorizationHeader() throws Exception {
        // Setup
        when(request.getHeader("Authorization")).thenReturn(null);

        // Execute
        tokenProcessor.doFilterInternal(request, response, filterChain);

        // Verify
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(tokenRepo);
    }

    @Test
    void testDoFilterInternal_WithValidTokenAndUserFound() throws Exception {
        // Setup
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        AuthenticationInfo authInfo = new AuthenticationInfo();
        authInfo.setUserId(UUID.randomUUID());
        authInfo.setRoles(List.of("ADMIN"));

        when(tokenRepo.infoForToken(eq("valid-token"))).thenReturn(authInfo);

        // Execute
        tokenProcessor.doFilterInternal(request, response, filterChain);

        // Verify
        verify(filterChain).doFilter(request, response);
        verify(tokenRepo).infoForToken(eq("valid-token"));

        // Verify security context was set
        PreAuthenticatedAuthenticationToken authentication =
            (PreAuthenticatedAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertNotNull( authentication );
        assertEquals(authInfo, authentication.getPrincipal() );
    }

    @Test
    void testDoFilterInternal_WithValidTokenAndUserNotFound() throws Exception {
        // Setup
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(tokenRepo.infoForToken(eq("invalid-token"))).thenReturn(null);

        // Execute
        tokenProcessor.doFilterInternal(request, response, filterChain);

        // Verify
        verify(filterChain,never()).doFilter(request, response);
        verify(tokenRepo).infoForToken(eq("invalid-token"));
    }

    @Test
    void testDoFilterInternal_WithValidTokenAndNoRoles() throws Exception {
        // Setup
        when(request.getHeader("Authorization")).thenReturn("Bearer token-without-roles");
        AuthenticationInfo authInfo = new AuthenticationInfo();
        authInfo.setUserId(UUID.randomUUID());
        // No roles set

        when(tokenRepo.infoForToken(eq("token-without-roles"))).thenReturn(authInfo);

        // Execute
        tokenProcessor.doFilterInternal(request, response, filterChain);

        // Verify
        verify(filterChain).doFilter(request, response);
        verify(tokenRepo).infoForToken(eq("token-without-roles"));

        // Security context should still be set but with no authorities
        PreAuthenticatedAuthenticationToken authentication =
            (PreAuthenticatedAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertNotNull( authentication );
        assertEquals(authInfo, authentication.getPrincipal() );
    }
}