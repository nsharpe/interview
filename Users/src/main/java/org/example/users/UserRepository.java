package org.example.users;

import java.util.UUID;

public interface UserRepository {

    UserModel getUser(long i);

    UserModel getUser(UUID publicId);

    UserModel createUser(UserModel model);

    UserModel updateUser(long id, UpdateUserModel updateUserModel);

    void deleteUser(long id);
}
