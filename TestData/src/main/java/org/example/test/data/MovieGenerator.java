package org.example.test.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.media.management.sdk.models.EpisodeModel;
import org.example.media.management.sdk.models.SeasonModel;
import org.example.media.management.sdk.models.SeriesCreateModel;
import org.example.media.management.sdk.models.SeriesModel;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.example.test.data.TestUtil.FAKER;

@Service
public class MovieGenerator implements Generator<MovieGenerator.MovieInput, SeriesModel>{
    private static final Duration MIN_LENGTH = Duration.ofMinutes(30);
    private static final Duration MAX_LENGTH = Duration.ofHours(4);

    private final SeriesGenerator seriesGenerator;
    private final SeasonGenerator seasonGenerator;
    private final EpisodeGenerator episodeGenerator;

    public MovieGenerator(SeriesGenerator seriesGenerator, SeasonGenerator seasonGenerator, EpisodeGenerator episodeGenerator) {
        this.seriesGenerator = Objects.requireNonNull(seriesGenerator);
        this.seasonGenerator = Objects.requireNonNull(seasonGenerator);
        this.episodeGenerator = Objects.requireNonNull(episodeGenerator);
    }


    @Override
    public SeriesModel save(MovieInput input) {
        SeriesModel seriesModel = seriesGenerator.generate(
          x->x.locale("en")
                  .seriesType(SeriesCreateModel.SeriesTypeEnum.MOVIE)
                  .title(input.title)
        );

        SeasonModel seasonModel = seasonGenerator.generate(
                x->x.toBuilder()
                        .seasonCreateModel(x.seasonCreateModel()
                                .order(0)
                                .title("movie 1")
                        )
                        .seriesId(seriesModel.getId())
                        .build()
        );

        episodeGenerator.generate(
                x -> {
                    x.getEpisodeCreateModel()
                            .setLength(input.length.toString());
                    x.getEpisodeCreateModel()
                            .setOrder(0);
                    x.getEpisodeCreateModel()
                            .setTitle(input.title);
                   return x.toBuilder()
                            .seriesId(seriesModel.getId())
                            .seasonid(seasonModel.getId())
                            .build();
                }
        );

        return seriesModel;
    }

    @Override
    public MovieInput generateInput() {

        String title = null;
        switch (ThreadLocalRandom.current().nextInt(3)){
            case 0:
                title = FAKER.word().noun() + " " + FAKER.word().conjunction() + " " + FAKER.word().noun();
                break;
            case 1:
                title = FAKER.word().noun() + " " + FAKER.word().verb();
                break;
            case 2:
                title = FAKER.word().noun() + " " + FAKER.word().verb() + " " + FAKER.word().adverb();
                break;
            default:
                title = FAKER.word().noun() + " " + UUID.randomUUID();
        }

        return MovieInput.builder()
                .length(Duration.ofSeconds(ThreadLocalRandom.current().nextLong(MIN_LENGTH.toSeconds(),MAX_LENGTH.toSeconds())))
                .title(title)
                .build();
    }

    @Getter
    @Setter
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MovieInput{
        private String title;
        private Duration length;
    }
}
