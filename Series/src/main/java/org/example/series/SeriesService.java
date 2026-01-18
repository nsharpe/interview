package org.example.series;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.NotFoundException;
import org.example.series.mysql.SeriesMysql;
import org.example.series.mysql.SeriesMysqlRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
public class SeriesService {

    private SeriesMysqlRepository repository;

    public SeriesModel getSeries(long id){
        return repository.findById(id)
                .map(SeriesMysql::toModel)
                .orElseThrow(()->new NotFoundException("series",String.valueOf(id)));
    }

    public SeriesModel getSeries(UUID publicId){
        return repository.findByPublicId(publicId)
                .map(SeriesMysql::toModel)
                .orElseThrow(()->new NotFoundException("series",publicId));
    }

    public SeriesModel createSeries(SeriesCreateModel model){
        return repository.save(SeriesMysql.of(model)).toModel();
    }

    public SeriesModel updateSeries(SeriesUpdateModel seriesModel, UUID uuid){
        return repository.save(SeriesMysql.of(seriesModel,uuid)).toModel();
    }

    @Transactional
    public void deleteSeries(UUID id){
        repository.deleteByPublicId(id);
    }
}
