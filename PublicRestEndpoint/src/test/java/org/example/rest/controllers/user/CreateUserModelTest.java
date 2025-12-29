package org.example.rest.controllers.user;

import org.example.service.UserModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserModelTest {


    @Test
    void testMapping(){
        CreateUserModel createUserModel = CreateUserModel.builder()
                .email("test@test.com")
                .firstName("first")
                .lastName("last")
                .build();

        UserModel userModel = createUserModel.toUserModel();

        assertEquals("test@test.com", userModel.getEmail());
        assertEquals("first", userModel.getFirstName());
        assertEquals("last", userModel.getLastName());
    }
}