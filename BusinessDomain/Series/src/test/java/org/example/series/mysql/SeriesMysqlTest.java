package org.example.series.mysql;

import org.example.mysql.MysqlTimeStamp;
import org.example.series.SeriesCreateModel;
import org.example.series.SeriesModel;
import org.example.series.SeriesType;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SeriesMysqlTest {


    @Test
    void testToModel(){
        UUID publicId = UUID.randomUUID();
        SeriesType seriesType = SeriesType.TV_SERIES;
        OffsetDateTime created = OffsetDateTime.now().minusDays(1);
        OffsetDateTime updated = OffsetDateTime.now();
        SeriesMysql seriesMysql = SeriesMysql.builder()
                .seriesType(seriesType)
                .title("title")
                .description("description")
                .locale(Locale.ENGLISH)
                .id(1L)
                .publicId(publicId)
                .timeStamp(MysqlTimeStamp.builder()
                        .creationTimestamp(created)
                        .lastUpdatedDate(updated)
                        .build())
                .build();

        SeriesModel model = seriesMysql.toModel();

        assertEquals("title", model.getTitle());
        assertEquals("description",model.getDescription());
        assertEquals(publicId,model.getId());
        assertEquals(Locale.ENGLISH,model.getLocale());
        assertEquals(updated,model.getLastUpdate());
        assertEquals(created,model.getCreationTimestamp());

        assertEquals(seriesType, model.getSeriesType());
    }

    @Test
    void testFromModel(){
        SeriesType seriesType = SeriesType.TV_SERIES;
        String title = "title";
        String description = "description";
        SeriesCreateModel seriesCreateModel = SeriesCreateModel.builder()
                .seriesType(seriesType)
                .title(title)
                .description(description)
                .build();

        SeriesMysql seriesMysql = SeriesMysql.of(seriesCreateModel);

        assertEquals(title,seriesMysql.getTitle());
        assertEquals(description,seriesMysql.getDescription());
        assertEquals(seriesType,seriesMysql.getSeriesType());
    }
}