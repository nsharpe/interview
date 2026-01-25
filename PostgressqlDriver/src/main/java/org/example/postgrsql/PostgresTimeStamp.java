package org.example.postgrsql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PostgresTimeStamp {

    @CreationTimestamp
    @Column(name="creation_timestamp",
            updatable = false)
    private OffsetDateTime creationTimestamp;

    @UpdateTimestamp
    @Column(name = "last_updated_date")
    private OffsetDateTime lastUpdatedDate;
}
