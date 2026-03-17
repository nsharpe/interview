package org.example.metric.media.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/media/{mediaId}")
public class MediaPerformanceController {

    @Autowired
    @Qualifier("postgresJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    private static final BeanPropertyRowMapper<MediaMetricModel> ROW_MAPPER = new BeanPropertyRowMapper<>(MediaMetricModel.class);

    @Operation(summary = "Get metrics about a customer's interactions for a piece of media",
            responses = {
                    @ApiResponse(description = "The metrics for a piece of media",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MediaMetricModel.class)))})
    @GetMapping("/metrics/play")
    public MediaMetricModel getMediaViewTime(@PathVariable UUID mediaId) {
        // SQL query to calculate total view time and total views for a specific media item
        String sql = """
            
            SELECT
            SUM(view_time_ms) as total_play_time_millis,
            COUNT(*) as total_plays
            FROM (
                  SELECT
                     mp_start.media_id as media_id,
                     (mp_stop.media_timestamp_ms - mp_start.media_timestamp_ms) as view_time_ms
                  FROM users.media_player_start mp_start
                  LEFT JOIN users.media_player_stop mp_stop on mp_start."event_id" = mp_stop."last_action_id"
                  LEFT JOIN users.external_user eu on eu.public_id = uuid(mp_start.user_id)
                  WHERE mp_start.media_id = ?
           ) as emivtm
            """;

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, mediaId.toString());
    }
}
