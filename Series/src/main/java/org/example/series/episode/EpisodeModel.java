package org.example.series.episode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeModel {

    private UUID publicId;
    private String title;
    private Duration length;
    private int season;
    private int episode;
    private UUID seriesPublicId;
}
