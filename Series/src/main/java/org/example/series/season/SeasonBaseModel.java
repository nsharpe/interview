package org.example.series.season;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;


@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class SeasonBaseModel implements Comparable<SeasonBaseModel>, Serializable {

    public static final Comparator<SeasonBaseModel> COMPARATOR = Comparator.nullsFirst(Comparator.comparingInt(SeasonBaseModel::getOrder));

    private UUID seriesId;
    private int order;
    private String title;

    @Override
    public int compareTo(SeasonBaseModel o) {
        return COMPARATOR.compare(this,o);
    }
}
