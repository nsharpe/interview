package org.example.repository;

import org.example.service.series.SeriesModel;
import org.example.service.user.UpdateUserModel;
import org.example.service.user.UserModel;

import java.util.UUID;

public interface SeriesRepository {

    SeriesModel getSeries(long id);

    SeriesModel getSeries(UUID publicId);

    SeriesModel createSeries(SeriesModel model);

    SeriesModel updateSeries(SeriesModel seriesModel);

    void deleteSeries(long id);
}
