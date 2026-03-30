package org.example.metric.media.controller;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaMetricModel {

    @Column(name = "total_plays")
    private Long totalPlays;
    @Column(name = "total_play_time_ms")
    private Long totalPlayTimeMillis;

    @Transient
    public BigDecimal getAveragePlayTime() {
        if( totalPlays == null || totalPlays == 0){
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(totalPlayTimeMillis)
                .divide(BigDecimal.valueOf(totalPlays), RoundingMode.DOWN)
                .setScale(2,RoundingMode.DOWN);
    }

}
