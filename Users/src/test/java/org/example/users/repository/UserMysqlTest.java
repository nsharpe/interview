package org.example.users.repository;

import org.example.users.UpdateUserModel;
import org.example.users.UserModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMysqlTest {

    @Test
    void testMapUserModelToDatabaseModel(){
        LocalDateTime now = LocalDateTime.now();
        UserModel userModel = UserModel.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("joe")
                .lastName("smith")
                .creationTimestamp(now)
                .build();

        UserMysql userMysql = UserMysql.of(userModel);

        assertEquals(userModel.getId(), userMysql.getId());
        assertEquals(userModel.getEmail(), userMysql.getEmail());
        assertEquals(userModel.getCreationTimestamp(),userMysql.getTimeStamp().getCreationTimestamp());
        assertEquals(userModel.getFirstName(),userMysql.getFirstName());
        assertEquals(userModel.getLastName(),userMysql.getLastName());
        assertNull(userMysql.getSoftDelete());
    }

    @Test
    void testUpdateDatabaseModel(){

        UserMysql userMysql = UserMysql.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("joe")
                .lastName("smith")
                .build();

        UpdateUserModel updateUserModel = UpdateUserModel.builder()
                .firstName("fjoe")
                .lastName("fsmith")
                .email("ftest@test.com")
                .build();

        userMysql.update(updateUserModel);
        assertEquals(updateUserModel.getFirstName(),userMysql.getFirstName());
        assertEquals(updateUserModel.getLastName(),userMysql.getLastName());
        assertEquals(updateUserModel.getEmail(),userMysql.getEmail());
    }
}