package org.example.users.repository;

import org.example.users.UpdateUserModel;
import org.example.users.UserModel;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserPostgresTest {

    @Test
    void testMapUserModelToDatabaseModel(){
        OffsetDateTime now = OffsetDateTime.now();
        UserModel userModel = UserModel.builder()
                .email("test@test.com")
                .firstName("joe")
                .lastName("smith")
                .creationTimestamp(now)
                .build();

        UserPostgres userPostgres = UserPostgres.of(userModel);

        assertEquals(userModel.getEmail(), userPostgres.getEmail());
        assertEquals(userModel.getCreationTimestamp(), userPostgres.getTimeStamp().getCreationTimestamp());
        assertEquals(userModel.getFirstName(), userPostgres.getFirstName());
        assertEquals(userModel.getLastName(), userPostgres.getLastName());
        assertNull(userPostgres.getSoftDelete());
    }

    @Test
    void testUpdateDatabaseModel(){

        UserPostgres userPostgres = UserPostgres.builder()
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

        userPostgres.update(updateUserModel);
        assertEquals(updateUserModel.getFirstName(), userPostgres.getFirstName());
        assertEquals(updateUserModel.getLastName(), userPostgres.getLastName());
        assertEquals(updateUserModel.getEmail(), userPostgres.getEmail());
    }
}