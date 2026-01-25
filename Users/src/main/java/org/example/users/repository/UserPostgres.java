package org.example.users.repository;


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
import org.example.postgrsql.PostgresSoftDelete;
import org.example.postgrsql.PostgresTimeStamp;
import org.example.users.UpdateUserModel;
import org.example.users.UserModel;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

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
public class UserPostgres {
    private static ModelMapper MODEL_MAPPER = new ModelMapper();
    static{
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(STRICT);
        MODEL_MAPPER.addMappings(new PropertyMap<UserPostgres, UserModel>() {
            @Override
            protected void configure() {
                map(source.getPublicId(),destination.getId());
            }
        });
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

    @Embedded
    private PostgresTimeStamp timeStamp;

    @Embedded
    private PostgresSoftDelete softDelete;

    public static UserPostgres of(UserModel userModel){
        UserPostgres toReturn = MODEL_MAPPER.map(userModel, UserPostgres.class);
        toReturn.setTimeStamp(PostgresTimeStamp.builder()
                .creationTimestamp(userModel.getCreationTimestamp())
                .build());
        return toReturn;
    }

    @PrePersist
    public void generatePublicUid() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID(); // Generate only if not already set
        }
    }

    @Transient
    public UserModel toModel(){
        UserModel toReturn = MODEL_MAPPER.map(this, UserModel.class);
        toReturn.setCreationTimestamp(this.timeStamp.getCreationTimestamp());
        toReturn.setLastUpdate(this.timeStamp.getLastUpdatedDate());
        return toReturn;
    }

    @Transient
    public void update(UpdateUserModel updateUserModel){
        MODEL_MAPPER.map(updateUserModel, this);
    }
}
