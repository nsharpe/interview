package org.amoeba.example.test.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amoeba.example.media.management.sdk.api.SeriesControllerApi;
import org.amoeba.example.media.management.sdk.models.SeriesCreateModel;
import org.amoeba.example.media.management.sdk.models.SeriesModel;
import org.springframework.stereotype.Service;

import static org.amoeba.example.test.data.TestUtil.FAKER;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeriesGenerator implements Generator<SeriesCreateModel,SeriesModel> {

    private final SeriesControllerApi seriesControllerApi;
    private final AuthenticationGenerator authenticationGenerator;

    @Override
    public SeriesModel save(SeriesCreateModel seriesCreateModel){
        seriesControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.getAdminBearerToken());
        return seriesControllerApi.create(seriesCreateModel)
                .doOnError(x->
                        log.atError()
                                .setCause(x)
                                .log("Error generating series")
                )
                .block();
    }

    @Override
    public SeriesCreateModel generateInput(){
        SeriesCreateModel seriesModel = new SeriesCreateModel();
        seriesModel.setDescription(FAKER.lorem().paragraph(3));
        seriesModel.setLocale(FAKER.locality().localeString());
        seriesModel.setTitle(FAKER.lorem().sentence(2,4));
        seriesModel.setSeriesType(SeriesCreateModel.SeriesTypeEnum.TV_SERIES);

        return seriesModel;
    }
}
