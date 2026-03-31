package org.amoeba.example.admin.user;

import lombok.RequiredArgsConstructor;
import org.amoeba.example.users.UpdateUserModel;
import org.amoeba.example.users.UserModel;
import org.amoeba.example.users.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "spring.security.enabled",havingValue = "false")
public class NoOpUserDatabase implements UserRepository {
    @Override
    public UserModel getUser(long i) {
        throw ex();
    }

    @Override
    public UserModel getUser(UUID publicId) {
        throw ex();
    }

    @Override
    public UserModel createUser(UserModel model) {
        throw new IllegalStateException("disabled as redis is disabled");
    }

    @Override
    public UserModel updateUser(UUID id, UpdateUserModel updateUserModel) {
        throw ex();
    }

    @Override
    public void deleteUser(UUID id) {
        throw ex();
    }

    @Override
    public String loginAs(UUID id) {
        throw ex();
    }

    @Override
    public Page<UserModel> getAllUsers(org.springframework.data.domain.Pageable pageable) {
        throw ex();
    }

    public IllegalStateException ex(){
        return new IllegalStateException("disabled as redis is disabled");
    }

}
