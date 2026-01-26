package org.example.series.mysql;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.Locale;
import java.util.UUID;

import static org.modelmapper.convention.MatchingStrategies.STRICT;


@Entity
@Table(name = "series")
@SQLDelete(sql = "UPDATE series SET deletion_timestamp = now() WHERE id=?")
@SQLRestriction("deletion_timestamp IS NULL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesMysql {
    private static ModelMapper MODEL_MAPPER = new ModelMapper();
    static{
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(STRICT);
        MODEL_MAPPER.addMappings(

        new PropertyMap<SeriesMysql, SeriesModel>() {
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

    @Column(unique = true)
    private String title;

    @Column(nullable = false)
    private Locale locale;

    @Column
    private String description;

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
    public SeriesModel toModel(){
        SeriesModel seriesModel = MODEL_MAPPER.map(this,SeriesModel.class);
        seriesModel.setCreationTimestamp(timeStamp.getCreationTimestamp());
        seriesModel.setLastUpdate(timeStamp.getLastUpdatedDate());
        return seriesModel;
    }

    public static SeriesMysql of(SeriesCreateModel seriesCreateModel){
        return MODEL_MAPPER.map(seriesCreateModel,SeriesMysql.class);
    }

    public static SeriesMysql of(SeriesUpdateModel seriesUpdateModel, UUID id){
        SeriesMysql toReturn = MODEL_MAPPER.map(seriesUpdateModel,SeriesMysql.class);
        toReturn.setPublicId(id);
        return toReturn;
    }
}
