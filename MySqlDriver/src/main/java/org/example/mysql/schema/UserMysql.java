package org.example.mysql.schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.example.service.UserModel;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;


@Entity
@Table(name = "external_user")
@SQLDelete(sql = "UPDATE external_user SET deletionDate = now() WHERE id=?")
public class UserMysql {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, updatable = false)
    private String email;
    private String name;
    private LocalDateTime creationDate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
