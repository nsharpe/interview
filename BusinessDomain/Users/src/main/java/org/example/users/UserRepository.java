package org.example.users;

import java.util.UUID;

public interface UserRepository {

    UserModel getUser(long i);

    UserModel getUser(UUID publicId);

    UserModel createUser(UserModel model);

    UserModel updateUser(UUID id, UpdateUserModel updateUserModel);

    void deleteUser(UUID id);

    String loginAs(UUID id);
}
