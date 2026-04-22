package org.amoeba.example.security.flux;

import org.amoeba.example.core.model.AuthenticationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenRepoTest {

    @Mock
    private RedisTemplate<String, AuthenticationInfo> redisTemplate;

    @Mock
    private ValueOperations<String, AuthenticationInfo> valueOps;

    private TokenRepo tokenRepo;

    @BeforeEach
    void setUp() {
        tokenRepo = new TokenRepo(redisTemplate);
    }

    @Test
    void testInfoForToken_WithValidToken() {
        // Setup mock
        AuthenticationInfo authInfo = new AuthenticationInfo();
        authInfo.setUserId(UUID.randomUUID());
        authInfo.setRoles(List.of("ADMIN"));

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(eq("Bearer valid-token"))).thenReturn(authInfo);

        // Execute
        AuthenticationInfo result = tokenRepo.infoForToken("valid-token");

        // Verify
        assertNotNull(result);
        assertNotNull(result.getUserId());
        assertEquals("valid-token", result.getToken());
        verify(redisTemplate).opsForValue();
        verify(valueOps).get(eq("Bearer valid-token"));
    }

    @Test
    void testInfoForToken_WithNullToken() {
        // Setup mock
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(any())).thenReturn(null);

        // Execute
        AuthenticationInfo result = tokenRepo.infoForToken(null);

        // Verify
        assertNull(result);
        verify(redisTemplate).opsForValue();
        verify(valueOps,never()).get(eq("Bearer invalid-token"));
    }

    @Test
    void testInfoForToken_WithEmptyToken() {
        // Setup mock
        AuthenticationInfo authInfo = new AuthenticationInfo();
        authInfo.setUserId(UUID.randomUUID());

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(eq("Bearer "))).thenReturn(authInfo);

        // Execute
        AuthenticationInfo result = tokenRepo.infoForToken("");

        // Verify
        assertNotNull(result);
        assertNotNull(result.getUserId());
        assertEquals("", result.getToken());
        verify(redisTemplate).opsForValue();
        verify(valueOps).get(eq("Bearer "));
    }

    @Test
    void testInfoForToken_WithNullAuthenticationInfo() {
        // Setup mock
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(any())).thenReturn(null);

        // Execute
        AuthenticationInfo result = tokenRepo.infoForToken("some-token");

        // Verify
        assertNull(result);
        verify(redisTemplate).opsForValue();
        verify(valueOps).get(eq("Bearer some-token"));
    }

    @Test
    void testInfoForToken_WithAuthenticationInfoAndRoles() {
        // Setup mock
        AuthenticationInfo authInfo = new AuthenticationInfo();
        authInfo.setUserId(UUID.randomUUID());
        authInfo.setRoles(List.of("USER", "ADMIN"));

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(eq("Bearer token-with-roles"))).thenReturn(authInfo);

        // Execute
        AuthenticationInfo result = tokenRepo.infoForToken("token-with-roles");

        // Verify
        assertNotNull(result);
        assertNotNull(result.getUserId());
        assertEquals("token-with-roles", result.getToken());
        assertNotNull(result.getRoles());
        assertEquals(2, result.getRoles().size());
        verify(redisTemplate).opsForValue();
        verify(valueOps).get(eq("Bearer token-with-roles"));
    }
}