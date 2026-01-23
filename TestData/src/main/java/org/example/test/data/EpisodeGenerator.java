package org.example.test.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.media.management.sdk.api.EpisodeControllerApi;
import org.example.media.management.sdk.models.EpisodeCreateModel;
import org.example.media.management.sdk.models.EpisodeModel;
import org.example.media.management.sdk.models.SeasonModel;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.test.data.TestUtil.FAKER;

@Service
public class EpisodeGenerator implements Generator<EpisodeGenerator.EpisodeInput, EpisodeModel> {

    private final EpisodeControllerApi episodeControllerApi;
    private final UUID baseSeries;
    private final UUID baseSeason;

    private final AtomicInteger episodeOrder = new AtomicInteger(0);

    public EpisodeGenerator(EpisodeControllerApi episodeControllerApi,
                            SeasonGenerator seasonGenerator) {
        this.episodeControllerApi = Objects.requireNonNull(episodeControllerApi);
        SeasonModel seasonModel = seasonGenerator.generate();
        this.baseSeries = seasonModel.getSeriesId();
        this.baseSeason = seasonModel.getId();
    }

    @Override
    public EpisodeModel save(EpisodeInput episodeInput){

        return episodeControllerApi.create2(episodeInput.seasonid, episodeInput.seriesId,episodeInput.episodeCreateModel)
                .block();
    }

    @Override
    public EpisodeInput generateInput(){
        EpisodeCreateModel episodeCreateModel = new EpisodeCreateModel();
        episodeCreateModel.setOrder(episodeOrder.getAndIncrement());
        episodeCreateModel.setTitle(FAKER.lorem().sentence(2,4));
        return new EpisodeInput(episodeCreateModel,baseSeries, baseSeason);
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class EpisodeInput{
        private EpisodeCreateModel episodeCreateModel;
        private UUID seriesId;
        private UUID seasonid;
    }
}
