package org.example.media.player.controller;

import org.example.media.player.controller.model.MediaPlayerEventBase;
import org.example.media.player.service.MediaEventService;
import org.example.security.AuthenticationInfo;
import org.example.security.SecurityConfiguration;
import org.example.security.TokenRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.example.core.util.Util.OBJECT_MAPPER;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MediaEventController.class)
@Import(SecurityConfiguration.class)
class MediaPlayerEventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MediaEventService eventService;

    @MockitoBean
    TokenRepo tokenRepo;

    @MockitoBean
    RedisConnectionFactory redisConnectionFactory;

    @Test
    void testAcceptStart() throws Exception{
        UUID userID = UUID.randomUUID();
        String token = "123";
        UUID mediaId = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        AuthenticationInfo authenticationInfo = AuthenticationInfo.builder()
                .userId(userID)
                .roles(List.of("SUBSCRIBER"))
                .build();

        MediaPlayerEventBase mediaPlayerEventBase = MediaPlayerEventBase.builder()
                .eventId(UUID.randomUUID())
                .startPosition(0)
                .timestamp(now)
                .build();

        MediaStartRequest startRequest = MediaStartRequest.builder()
                .eventState(mediaPlayerEventBase)
                .build();

        when(tokenRepo.infoForToken(token)).thenReturn(authenticationInfo);

        mockMvc.perform(post("/media/" + mediaId + "/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(OBJECT_MAPPER.writeValueAsString(startRequest))
                )
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))// Verify HTTP status
                .andExpect(jsonPath("$.actionId").value(startRequest.getEventState().getEventId().toString()));
    }
}