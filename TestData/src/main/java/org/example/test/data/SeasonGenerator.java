package org.example.test.data;

import org.example.media.management.sdk.api.SeasonControllerApi;
import org.example.media.management.sdk.invoker.ApiException;
import org.example.media.management.sdk.models.SeasonCreateModel;
import org.example.media.management.sdk.models.SeasonModel;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.test.data.TestUtil.FAKER;

@Service
public class SeasonGenerator implements Generator<SeasonGenerator.SeasonInput,SeasonModel> {

    private final SeasonControllerApi seasonControllerApi;
    private final UUID baseSeries;

    private final AtomicInteger seasonOrder = new AtomicInteger(0);

    public SeasonGenerator(SeasonControllerApi seasonControllerApi,
                           SeriesGenerator seriesGenerator) {
        this.seasonControllerApi = Objects.requireNonNull(seasonControllerApi);
        this.baseSeries = seriesGenerator.generate().getId();
    }

    @Override
    public SeasonModel save(SeasonGenerator.SeasonInput seasonCreateModel){
        try {
            return seasonControllerApi.create1(seasonCreateModel.seriesId,seasonCreateModel.seasonCreateModel);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SeasonInput generateInput(){
        SeasonCreateModel seasonCreateModel = new SeasonCreateModel();
        seasonCreateModel.setOrder(seasonOrder.getAndIncrement());
        seasonCreateModel.setTitle(FAKER.lorem().sentence(2,4));
        return new SeasonInput(seasonCreateModel,baseSeries);
    }

    public record SeasonInput(SeasonCreateModel seasonCreateModel, UUID seriesId){}
}
