package org.example.mysql.schema;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.modelmapper.ModelMapper;

import java.time.Duration;
import java.time.LocalDateTime;
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
    }

    // This id is intended to reduce join cost
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // You don't want the public to know how many records are in our database, thus 'id' is not made available
    @Column( name = "public_id", updatable = false, unique = true, nullable = false)
    private UUID publicId;

    private String title;
    private Duration length;
    private int season;
    private int episode;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private SeriesMysql series;

    @CreationTimestamp
    @Column(name="creation_timestamp",
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime creationTimestamp;

    @Column(name="deletion_timestamp")
    private LocalDateTime deletionTimestamp;

    @PrePersist
    public void generatePublicUid() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID(); // Generate only if not already set
        }
    }
}
