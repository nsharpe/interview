package org.example.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserRepository {

    UserModel getUser(long i);

    UserModel getUser(UUID publicId);

    UserModel createUser(UserModel model);

    UserModel updateUser(UUID id, UpdateUserModel updateUserModel);

    void deleteUser(UUID id);

    String loginAs(UUID id);

    Page<UserModel> getAllUsers(Pageable pageable);
}
