package org.example.series.season;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.NotFoundException;
import org.example.series.mysql.SeriesMysql;
import org.example.series.mysql.SeriesMysqlRepository;
import org.example.series.season.mysql.SeasonMysql;
import org.example.series.season.mysql.SeasonMysqlRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class SeasonService {

    private final SeasonMysqlRepository seasonMysqlRepository;
    private final SeriesMysqlRepository seriesMysqlRepository;


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

    public SeasonModel getSeason(UUID publicId, UUID seriesId){
        return seasonMysqlRepository.findByPublicId(publicId)
                .map(SeasonMysql::toModel)
                .filter(x-> seriesId.equals( x.getSeriesId()))
                .orElseThrow(()->new NotFoundException("series",publicId));
    }

    @Transactional
    public SeasonModel create(SeasonCreateModel model,UUID seriesId){
        SeriesMysql seriesMysql = seriesMysqlRepository.findByPublicId(seriesId)
                .orElseThrow(() -> new NotFoundException("series",seriesId));

        SeasonMysql seasonMysql = SeasonMysql.of(model);
        seasonMysql.setSeries(seriesMysql);

        return seasonMysqlRepository.save(seasonMysql).toModel();
    }

    @Transactional
    public SeasonModel updateSeason(SeasonUpdateModel seriesModel, UUID uuid, UUID seriesUuid){
        getSeason(uuid,seriesUuid);

        return seasonMysqlRepository.save(SeasonMysql.of(seriesModel,uuid)).toModel();
    }

    @Transactional
    public void deleteSeason(UUID id){
        seasonMysqlRepository.deleteByPublicId(id);
    }
}
