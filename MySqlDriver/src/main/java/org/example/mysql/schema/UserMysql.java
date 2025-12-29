package org.example.mysql.schema;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.service.UpdateUserModel;
import org.example.service.UserModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;


@Entity
@Table(name = "external_user")
@SQLDelete(sql = "UPDATE external_user SET deletion_date = now() WHERE id=?")
@SQLRestriction("deletion_date IS NULL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMysql {
    private static ModelMapper MODEL_MAPPER = new ModelMapper();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    @Transient
    public UserModel toModel(){
        return MODEL_MAPPER.map(this, UserModel.class);
    }

    @Transient
    public void update(UpdateUserModel updateUserModel){
        MODEL_MAPPER.map(updateUserModel, this);
    }
}
