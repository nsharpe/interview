package org.example.metric.media.controller;

import org.example.security.SecurityConfiguration;
import org.example.security.TokenRepo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MediaPerformanceController.class)
@Import(SecurityConfiguration.class)
class
MediaPerformanceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    @Qualifier("postgresJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @MockitoBean
    private TokenRepo tokenRepo;

    @MockitoBean
    RedisConnectionFactory redisConnectionFactory;

    @Test
    void testGetMediaViewTimeEndpoint() throws Exception {
        UUID mediaId = UUID.randomUUID();
        String mediaIdString = mediaId.toString();

        MediaMetricModel mockModel = MediaMetricModel.builder()
                .totalPlays(5L)
                .totalPlayTimeMillis(300000L)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.<RowMapper>any(), anyString()))
                .thenReturn(mockModel);

        mockMvc.perform(get("/media/{mediaId}/metrics/play", mediaIdString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPlays").value(5))
                .andExpect(jsonPath("$.totalPlayTimeMillis").value(300000))
                .andExpect(jsonPath("$.averagePlayTime").value(60000.00)); // 300000 / 5 = 60000
    }
}