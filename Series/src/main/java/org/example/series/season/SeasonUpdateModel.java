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
public class SeasonUpdateModel extends SeasonBaseModel{

    @Serial
    private static final long serialVersionUID = 353704417708119910L;
}
