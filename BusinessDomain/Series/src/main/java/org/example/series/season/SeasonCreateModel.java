package org.example.series.season;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class SeasonCreateModel extends SeasonBaseModel{

    @Serial
    private static final long serialVersionUID = -6076249427719355215L;
}
