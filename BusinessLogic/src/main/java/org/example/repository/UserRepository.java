package org.example.repository;

import org.example.service.UpdateUserModel;
import org.example.service.UserModel;

public interface UserRepository {

    UserModel getUser(long i);

    UserModel createUser(UserModel model);

    UserModel updateUser(long id, UpdateUserModel updateUserModel);

    void deleteUser(long id);
}
