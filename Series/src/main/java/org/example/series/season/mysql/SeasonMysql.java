package org.example.series.season.mysql;


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
import org.example.series.mysql.SeriesMysql;
import org.example.series.season.SeasonCreateModel;
import org.example.series.season.SeasonModel;
import org.example.series.season.SeasonUpdateModel;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.modelmapper.ModelMapper;

import java.time.Duration;
import java.util.UUID;

import static org.modelmapper.convention.MatchingStrategies.STRICT;


@Entity
@Table(name = "season")
@SQLDelete(sql = "UPDATE season SET deletion_timestamp = now() WHERE id=?")
@SQLRestriction("deletion_timestamp IS NULL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeasonMysql {
    private static ModelMapper MODEL_MAPPER = new ModelMapper();
    static{
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(STRICT);
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
    @Column(nullable = false)
    private int order;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false, updatable = false)
    private SeriesMysql series;

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
    public SeasonModel toModel(){
        SeasonModel seasonModel = MODEL_MAPPER.map(this,SeasonModel.class);
        seasonModel.setCreationTimestamp(timeStamp.getCreationTimestamp());
        seasonModel.setLastUpdate(timeStamp.getLastUpdatedDate());
        return seasonModel;
    }

    public static SeasonMysql of(SeasonCreateModel seriesCreateModel){
        return MODEL_MAPPER.map(seriesCreateModel,SeasonMysql.class);
    }

    public static SeasonMysql of(SeasonUpdateModel seriesUpdateModel, UUID id){
        SeasonMysql toReturn = MODEL_MAPPER.map(seriesUpdateModel,SeasonMysql.class);
        toReturn.setPublicId(id);
        return toReturn;
    }
}
