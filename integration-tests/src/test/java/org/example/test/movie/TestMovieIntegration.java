package org.example.test.movie;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.integration.util.TimeTestUtil;
import org.example.media.management.sdk.api.EpisodeControllerApi;
import org.example.media.management.sdk.api.SeasonControllerApi;
import org.example.media.management.sdk.api.SeriesControllerApi;
import org.example.media.management.sdk.models.EpisodeModel;
import org.example.media.management.sdk.models.SeasonModel;
import org.example.media.management.sdk.models.SeriesModel;
import org.example.media.management.sdk.models.SeriesPage;
import org.example.test.data.AuthenticationGenerator;
import org.example.test.data.EpisodeGenerator;
import org.example.test.data.MovieGenerator;
import org.example.test.data.SeasonGenerator;
import org.example.test.data.SeriesGenerator;
import org.example.test.util.TestContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.example.test.data.PostPayloadGenerator.createSeriesPojo;
import static org.example.test.util.TestMapper.MAPPER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestMovieIntegration extends TestContainers {

    @Autowired
    private EpisodeControllerApi episodeControllerApi;
    @Autowired
    private SeasonControllerApi seasonControllerApi;
    @Autowired
    private SeriesControllerApi seriesControllerApi;
    @Autowired
    private AuthenticationGenerator authenticationGenerator;
    @Autowired
    private MovieGenerator movieGenerator;

    @BeforeEach
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void beforeEach() {
        RestAssured.baseURI = "http://localhost:"+getMediaManagmentPort();
        seriesControllerApi.getApiClient().setBearerToken(authenticationGenerator.getAdminBearerToken());
        seasonControllerApi.getApiClient().setBearerToken(authenticationGenerator.getAdminBearerToken());
        episodeControllerApi.getApiClient().setBearerToken(authenticationGenerator.getAdminBearerToken());
    }

    @Test
    void testGenerateMovie() throws Exception {
        SeriesModel seriesModel = movieGenerator.generate();
        assertNotNull(seriesModel);
        assertNotNull(seriesModel.getId());

         List<UUID> seasonId = seasonControllerApi.getAllSeasonId(seriesModel.getId()).collect(Collectors.toList()).block();

         assertNotNull(seasonId);
         assertEquals(1,seasonId.size());

         List<UUID> episodeId = episodeControllerApi.getAllEpisodesForSeason(seasonId.getFirst(),seriesModel.getId())
                 .collect(Collectors.toList())
                 .block();

         assertNotNull(episodeId);
         assertEquals(1,episodeId.size());

         assertEquals(episodeId.getFirst(), seriesControllerApi.getFirstEpisodeId(seriesModel.getId()).blockFirst());

    }
}
