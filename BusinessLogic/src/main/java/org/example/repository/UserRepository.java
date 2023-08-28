package org.example.repository;

import org.example.service.UserModel;

public interface UserRepository {

    UserModel getUser(int i);

    UserModel createUser(UserModel model);
}
