package org.example.series.episode.mysql;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.mysql.MysqlSoftDelete;
import org.example.mysql.MysqlTimeStamp;
import org.example.series.SeriesCreateModel;
import org.example.series.SeriesModel;
import org.example.series.SeriesUpdateModel;
import org.example.series.episode.EpisodeCreateModel;
import org.example.series.episode.EpisodeModel;
import org.example.series.episode.EpisodeUpdateModel;
import org.example.series.mysql.SeriesMysql;
import org.example.series.season.mysql.SeasonMysql;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.time.Duration;
import java.util.UUID;

import static org.modelmapper.convention.MatchingStrategies.STRICT;


@Entity
@Table(name = "episode")
@SQLDelete(sql = "UPDATE episode SET deletion_timestamp = now() WHERE id=?")
@SQLRestriction("deletion_timestamp IS NULL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodeMysql {
    private static ModelMapper MODEL_MAPPER = new ModelMapper();
    static{
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(STRICT);
        MODEL_MAPPER.addMappings(

                new PropertyMap<EpisodeMysql, EpisodeModel>() {
                    @Override
                    protected void configure() {
                        map(source.getPublicId(),destination.getId());
                    }
                });
    }

    // This id is intended to reduce join cost
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // You don't want the public to know how many records are in our database, thus 'id' is not made available
    @Column( name = "public_id", updatable = false, unique = true, nullable = false)
    private UUID publicId;

    @Column(nullable = false)
    private String title;
    private Duration length;
    @Column(name = "episode_order", nullable = false)
    private int order;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private SeasonMysql season;

    @Embedded
    private MysqlTimeStamp timeStamp;

    @Embedded
    private MysqlSoftDelete delete;

    @PrePersist
    public void generatePublicUid() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID(); // Generate only if not already set
        }
    }

    @Transient
    public EpisodeModel toModel(){
        EpisodeModel episodeModel = MODEL_MAPPER.map(this,EpisodeModel.class);
        episodeModel.setCreationTimestamp(timeStamp.getCreationTimestamp());
        episodeModel.setLastUpdate(timeStamp.getLastUpdatedDate());
        return episodeModel;
    }

    public static EpisodeMysql of(EpisodeCreateModel createModel){
        return MODEL_MAPPER.map(createModel,EpisodeMysql.class);
    }

    public static EpisodeMysql of(EpisodeUpdateModel seriesUpdateModel, UUID id){
        EpisodeMysql toReturn = MODEL_MAPPER.map(seriesUpdateModel,EpisodeMysql.class);
        toReturn.setPublicId(id);
        return toReturn;
    }

}
