package org.amoeba.example.metric.media.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaPerformanceControllerTest {

    @Mock
    @Qualifier("postgresJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MediaPerformanceController controller;

    @Test
    @SuppressWarnings("unchecked")
    void testGetMediaViewTime_Success() {
        UUID mediaId = UUID.randomUUID();
        MediaMetricModel expectedModel = MediaMetricModel.builder()
                .totalPlays(5L)
                .totalPlayTimeMillis(300000L)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyString()))
                .thenReturn(expectedModel);

        MediaMetricModel result = controller.getMediaViewTime(mediaId);

        assertNotNull(result);
        assertEquals(expectedModel.getTotalPlays(), result.getTotalPlays());
        assertEquals(expectedModel.getTotalPlayTimeMillis(), result.getTotalPlayTimeMillis());
        assertEquals(expectedModel.getAveragePlayTime(), result.getAveragePlayTime());

        verify(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class), eq(mediaId.toString()));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetMediaViewTime_WithNullValues() {
        UUID mediaId = UUID.randomUUID();
        MediaMetricModel expectedModel = MediaMetricModel.builder()
                .totalPlays(null)
                .totalPlayTimeMillis(null)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyString()))
                .thenReturn(expectedModel);

        MediaMetricModel result = controller.getMediaViewTime(mediaId);

        assertNotNull(result);
        assertNull(result.getTotalPlays());
        assertNull(result.getTotalPlayTimeMillis());
        assertEquals(BigDecimal.ZERO, result.getAveragePlayTime());

        verify(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class), eq(mediaId.toString()));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetMediaViewTime_CalculatesAverageCorrectly() {
        UUID mediaId = UUID.randomUUID();
        MediaMetricModel expectedModel = MediaMetricModel.builder()
                .totalPlays(10L)
                .totalPlayTimeMillis(500000L)
                .build();

        when(jdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyString()))
                .thenReturn(expectedModel);

        MediaMetricModel result = controller.getMediaViewTime(mediaId);

        assertNotNull(result);
        assertEquals(50000L, result.getAveragePlayTime().longValue()); // 500000 / 10 = 50000

        verify(jdbcTemplate).queryForObject(anyString(), any(BeanPropertyRowMapper.class), eq(mediaId.toString()));
    }
}