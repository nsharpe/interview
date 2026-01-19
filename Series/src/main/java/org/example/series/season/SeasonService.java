package org.example.series.season;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.NotFoundException;
import org.example.series.mysql.SeriesMysql;
import org.example.series.season.mysql.SeasonMysql;
import org.example.series.season.mysql.SeasonMysqlRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@RequiredArgsConstructor
public class SeasonService {

    private SeasonMysqlRepository seasonMysqlRepository;


    public SeasonModel getSeason(long id){
        return seasonMysqlRepository.findById(id)
                .map(SeasonMysql::toModel)
                .orElseThrow(()->new NotFoundException("series",String.valueOf(id)));
    }

    public SeasonModel getSeason(UUID publicId){
        return seasonMysqlRepository.findByPublicId(publicId)
                .map(SeasonMysql::toModel)
                .orElseThrow(()->new NotFoundException("series",publicId));
    }

    public SeasonModel createSeries(SeasonCreateModel model){
        return seasonMysqlRepository.save(SeasonMysql.of(model)).toModel();
    }

    public SeasonModel updateSeries(SeasonUpdateModel seriesModel, UUID uuid){
        return seasonMysqlRepository.save(SeasonMysql.of(seriesModel,uuid)).toModel();
    }

    @Transactional
    public void deleteSeries(UUID id){
        seasonMysqlRepository.deleteByPublicId(id);
    }
}
