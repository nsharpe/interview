package org.example.mysql.schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.example.service.UpdateUserModel;
import org.example.service.UserModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;


@Entity
@Table(name = "external_user")
@SQLDelete(sql = "UPDATE external_user SET deletion_date = now() WHERE id=?")
@Where(clause="deletion_date IS NULL")
public class UserMysql {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, updatable = false)
    private String email;
    private String name;

    @CreationTimestamp
    @Column(name="creation_date",
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime creationDate;

    @Column(name="deletion_date")
    private LocalDateTime deletionDate;

    public UserMysql(){
        // NOOP: required to make jpa work
    }

    public UserMysql(UserModel userModel){
        this.id = userModel.getId();
        this.email = userModel.getEmail();
        this.name = userModel.getName();
        this.creationDate = userModel.getCreatedOn();
    }


    @Transient
    public UserModel toModel(){
        UserModel toReturn = new UserModel();

        toReturn.setCreatedOn(creationDate);
        toReturn.setId(id);
        toReturn.setEmail(email);
        toReturn.setName(name);

        return toReturn;
    }

    @Transient
    public void update(UpdateUserModel updateUserModel){
        setName(updateUserModel.getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(LocalDateTime deletionDate) {
        this.deletionDate = deletionDate;
    }
}
