package org.amoeba.example.security.flux;

import org.amoeba.example.core.model.AuthenticationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenProcessorTest {

    @Mock
    private TokenRepo tokenRepo;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private ServerHttpRequest request;

    @Mock
    private HttpHeaders requestHeaders;

    @Mock
    private WebFilterChain filterChain;

    private TokenProcessor tokenProcessor;

    @BeforeEach
    void setUp() {
        tokenProcessor = new TokenProcessor(tokenRepo);
        // Clear security context before each test
        SecurityContextHolder.clearContext();

        when(exchange.getRequest()).thenReturn(request);
        when(request.getHeaders()).thenReturn(requestHeaders);
    }

    @Test
    void testDoFilterInternal_WithInvalidAuthorizationHeader() throws Exception {
        // Setup
        when(requestHeaders.getFirst("Authorization")).thenReturn( "InvalidHeader");
        when(filterChain.filter(any())).thenReturn(Mono.empty());

        StepVerifier.create( tokenProcessor.filter(exchange, filterChain))
                .verifyComplete();

        // Verify
        verify(filterChain).filter(exchange);
        verifyNoInteractions(tokenRepo);
    }

    @Test
    void testDoFilterInternal_WithNullAuthorizationHeader() throws Exception {
        // Setup
        when(requestHeaders.getFirst("Authorization")).thenReturn(null);
        when(filterChain.filter(any())).thenReturn(Mono.empty());

        // Execute
        StepVerifier.create(tokenProcessor.filter(exchange, filterChain))
                .verifyComplete();

        // Verify
        verify(filterChain).filter(exchange);
        verifyNoInteractions(tokenRepo);
    }

    @Test
    void testDoFilterInternal_WithValidTokenAndUserFound() throws Exception {
        // Setup
        when(requestHeaders.getFirst("Authorization")).thenReturn("Bearer valid-token");
        AuthenticationInfo authInfo = new AuthenticationInfo();
        authInfo.setUserId(UUID.randomUUID());
        authInfo.setRoles(List.of("ADMIN"));

        when(tokenRepo.infoForToken(eq("valid-token"))).thenReturn(authInfo);

        AtomicReference<AuthenticationInfo> capturedPrincipal = new AtomicReference<>();

        filterChain = (exch) ->
                SecurityConfiguration.getAuthenticationInfo()
                        .doOnNext(capturedPrincipal::set)
                        .then();

        // Execute
        StepVerifier.create(
                        tokenProcessor.filter(exchange, filterChain)
                                .then(SecurityConfiguration.getAuthenticationInfo())
                )
                .verifyComplete();

        // Verify Interactions
        assertNotNull(capturedPrincipal.get());
        assertEquals(authInfo.getUserId(), capturedPrincipal.get().getUserId());
        assertEquals(authInfo, capturedPrincipal.get());
    }

    @Test
    void testDoFilterInternal_WithValidTokenAndUserNotFound() throws Exception {
        // Setup
        when(requestHeaders.getFirst("Authorization")).thenReturn("Bearer invalid-token");
        when(tokenRepo.infoForToken(eq("invalid-token"))).thenReturn(null);

        AtomicReference<AuthenticationInfo> capturedPrincipal = new AtomicReference<>();

        filterChain = (exch) ->
                SecurityConfiguration.getAuthenticationInfo()
                        .doOnNext(capturedPrincipal::set)
                        .then();

        // Execute
        StepVerifier.create(tokenProcessor.filter(exchange, filterChain))
                .verifyComplete();

        // Verify
        assertNull(capturedPrincipal.get());
    }

    @Test
    void testDoFilterInternal_WithValidTokenAndNoRoles() throws Exception {
        // Setup
        when(requestHeaders.getFirst("Authorization")).thenReturn("Bearer token-without-roles");
        AuthenticationInfo authInfo = new AuthenticationInfo();
        authInfo.setUserId(UUID.randomUUID());
        // No roles set

        when(tokenRepo.infoForToken(eq("token-without-roles"))).thenReturn(authInfo);

        AtomicReference<AuthenticationInfo> capturedPrincipal = new AtomicReference<>();

        filterChain = (exch) ->
                SecurityConfiguration.getAuthenticationInfo()
                        .doOnNext(capturedPrincipal::set)
                        .then();

        // Execute
        StepVerifier.create(tokenProcessor.filter(exchange, filterChain))
                .verifyComplete();

        // Security context should still be set but with no authorities
        assertNotNull(capturedPrincipal.get());
        assertEquals(authInfo.getUserId(), capturedPrincipal.get().getUserId());
        assertEquals(authInfo, capturedPrincipal.get());

        assertTrue( authInfo.getRoles().isEmpty());
    }
}