package org.example.mysql.schema;


import jakarta.persistence.Column;
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
import org.example.service.user.UpdateUserModel;
import org.example.service.user.UserModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MatchingStrategy;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.modelmapper.convention.MatchingStrategies.STRICT;


@Entity
@Table(name = "external_user")
@SQLDelete(sql = "UPDATE external_user SET deletion_timestamp = now() WHERE id=?")
@SQLRestriction("deletion_timestamp IS NULL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMysql {
    private static ModelMapper MODEL_MAPPER = new ModelMapper();
    static{
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(STRICT);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // You don't want the public to know how many records are in our database, thus 'id' is not made available
    @Column( name = "public_id", updatable = false, unique = true, nullable = false)
    private UUID publicId;
    @Column(unique = true, updatable = false)
    private String email;
    private String firstName;
    private String lastName;

    @CreationTimestamp
    @Column(name="creation_timestamp",
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime creationTimestamp;

    @Column(name="deletion_timestamp")
    private LocalDateTime deletionTimestamp;

    public static UserMysql of(UserModel userModel){
        return MODEL_MAPPER.map(userModel, UserMysql.class);
    }

    @PrePersist
    public void generatePublicUid() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID(); // Generate only if not already set
        }
    }

    @Transient
    public UserModel toModel(){
        return MODEL_MAPPER.map(this, UserModel.class);
    }

    @Transient
    public void update(UpdateUserModel updateUserModel){
        MODEL_MAPPER.map(updateUserModel, this);
    }
}
