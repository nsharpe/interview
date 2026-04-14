package org.amoeba.example.comment.repository;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.amoeba.example.postgrsql.PostgresSoftDelete;
import org.amoeba.example.postgrsql.PostgresTimeStamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "comment", schema = "media")
@SQLDelete(sql = "UPDATE comment.comment SET deletion_timestamp = now() WHERE id=?")
@SQLRestriction("deletion_timestamp IS NULL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // You don't want the public to know how many records are in our database, thus 'id' is not made available
    @Column( name = "public_id", updatable = false, unique = true, nullable = false)
    private UUID publicId;
    @Column(updatable = false, nullable = false)
    private UUID userId;

    @Column(updatable = false, nullable = false)
    private String recordType;
    @Column(updatable = false, nullable = false)
    private UUID recordId;
    @Column(updatable = false, nullable = false, length = 1025)
    private String comment;

    @Column(updatable = false)
    private Long mediaPositionMs;

    @Embedded
    private PostgresTimeStamp timeStamp;

    @Embedded
    private PostgresSoftDelete softDelete;

    @PrePersist
    public void generatePublicUid() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID();
        }
    }
}
