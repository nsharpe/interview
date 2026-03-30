package org.example.series;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Locale;

/**
 * All infomration that is used on creating, updating or reading from a series
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
class SeriesBaseModel {
    private String title;
    private String description;
    @Builder.Default
    private Locale locale = Locale.US;

    private SeriesType seriesType;
}
