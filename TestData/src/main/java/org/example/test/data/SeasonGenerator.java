package org.example.test.data;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.media.management.sdk.api.SeasonControllerApi;
import org.example.media.management.sdk.models.SeasonCreateModel;
import org.example.media.management.sdk.models.SeasonModel;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.test.data.TestUtil.FAKER;

@Service
@Slf4j
public class SeasonGenerator implements Generator<SeasonGenerator.SeasonInput,SeasonModel> {

    private final SeasonControllerApi seasonControllerApi;
    private final UUID baseSeries;
    private final AuthenticationGenerator authenticationGenerator;

    private final AtomicInteger seasonOrder = new AtomicInteger(0);

    public SeasonGenerator(SeasonControllerApi seasonControllerApi,
                           SeriesGenerator seriesGenerator,
                           AuthenticationGenerator authenticationGenerator) {
        this.seasonControllerApi = Objects.requireNonNull(seasonControllerApi);
        this.baseSeries = seriesGenerator.generate().getId();
        this.authenticationGenerator=Objects.requireNonNull(authenticationGenerator);
    }

    @Override
    public SeasonModel save(SeasonGenerator.SeasonInput seasonCreateModel){
        seasonControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.getAdminBearerToken());
        return seasonControllerApi.create1(seasonCreateModel.seriesId,seasonCreateModel.seasonCreateModel)
                .doOnError(x->
                        log.atError()
                                .setCause(x)
                                .log("Error generating season")
                        )
                .block();
    }

    @Override
    public SeasonInput generateInput(){
        SeasonCreateModel seasonCreateModel = new SeasonCreateModel();
        seasonCreateModel.setOrder(seasonOrder.getAndIncrement());
        seasonCreateModel.setTitle(FAKER.lorem().sentence(2,4));
        return new SeasonInput(seasonCreateModel,baseSeries);
    }


    @Builder(toBuilder = true)
    public record SeasonInput(SeasonCreateModel seasonCreateModel, UUID seriesId){

    }
}
