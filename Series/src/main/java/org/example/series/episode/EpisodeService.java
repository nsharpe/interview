package org.example.series.episode;

import lombok.RequiredArgsConstructor;
import org.example.core.exceptions.NotFoundException;
import org.example.series.episode.mysql.EpisodeMysql;
import org.example.series.episode.mysql.EpisodeMysqlRepository;

import org.example.series.season.SeasonModel;
import org.example.series.season.SeasonService;
import org.example.series.season.mysql.SeasonMysql;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class EpisodeService {

    private final SeasonService seasonService;
    private final EpisodeMysqlRepository repository;


    public EpisodeModel get(long id) {
        return repository.findById(id)
                .map(EpisodeMysql::toModel)
                .orElseThrow(() -> new NotFoundException("episode", String.valueOf(id)));
    }

    public EpisodeModel get(UUID publicId) {
        return repository.findByPublicId(publicId)
                .map(EpisodeMysql::toModel)
                .orElseThrow(() -> new NotFoundException("episode", publicId));
    }

    public EpisodeModel get(UUID publicId, UUID seriesId, UUID seasonId) {
        return repository.findByPublicId(publicId)
                .map(EpisodeMysql::toModel)
                .filter(x -> seriesId.equals(x.getSeries()))
                .filter(x -> seasonId.equals(x.getSeason()))
                .orElseThrow(() -> new NotFoundException("episode", publicId));
    }

    @Transactional
    public EpisodeModel create(EpisodeCreateModel model, UUID seasonId, UUID seriesId) {
        SeasonModel season = seasonService.getSeason(seasonId, seriesId);

        EpisodeMysql episodeModel = EpisodeMysql.of(model);
        episodeModel.setSeason(new SeasonMysql());
        episodeModel.getSeason().setId(season.getInternalId());


        return repository.save(episodeModel).toModel();
    }

    @Transactional
    public EpisodeModel update(EpisodeUpdateModel model, UUID uuid, UUID seasonId, UUID seriesId) {
        SeasonModel season = seasonService.getSeason(seasonId, seriesId);

        EpisodeMysql episodeModel = EpisodeMysql.of(model, uuid);
        episodeModel.setSeason(new SeasonMysql());
        episodeModel.getSeason().setId(season.getInternalId());

        return repository.save(episodeModel).toModel();
    }

    @Transactional
    public void delete(UUID id, UUID seasonId, UUID seriesId) {
        seasonService.getSeason(seasonId,seriesId);
        repository.deleteByPublicId(id);
    }
}
