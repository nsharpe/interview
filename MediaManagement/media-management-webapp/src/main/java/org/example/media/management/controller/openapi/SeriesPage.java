package org.example.media.management.controller.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.series.SeriesModel;
import org.example.web.OpenApiPage;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesPage extends OpenApiPage {
    @Schema(description = "The list of series")
    private List<SeriesModel> content;
}
