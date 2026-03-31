package org.amoeba.example.rest.database;

import lombok.RequiredArgsConstructor;
import org.amoeba.example.core.exceptions.NotFoundException;

import org.amoeba.example.users.UpdateUserModel;
import org.amoeba.example.users.UserModel;
import org.amoeba.example.users.UserRepository;
import org.amoeba.example.users.repository.UserPostgres;
import org.amoeba.example.users.repository.UserCrudRespoitory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDatabase implements UserRepository {

    private final UserCrudRespoitory userCrudRespoitory;

    @Override
    @Cacheable(value = "users",key = "#id")
    public UserModel getUser(long id) {
        return  userCrudRespoitory.findById(id)
                .orElseThrow( () -> new NotFoundException("user",id) )
                .toModel();
    }

    @Override
    @Cacheable(value = "users",key = "#publicId")
    public UserModel getUser(UUID publicId) {
        return  userCrudRespoitory.findByPublicId(publicId)
                .orElseThrow( () -> new NotFoundException("user", publicId))
                .toModel();
    }

    @Override
    @CacheEvict(value = "users",key = "#result.getId()")
    public UserModel createUser(UserModel model) {
        return userCrudRespoitory.save(UserPostgres.of(model)).toModel();
    }

    @Override
    @CachePut(value = "users",key = "#id")
    public UserModel updateUser(UUID id, UpdateUserModel updateUserModel) {
        UserPostgres user = userCrudRespoitory.findByPublicId(id)
                .orElseThrow(() -> new NotFoundException("User " + id +" not found"));

        user.update(updateUserModel);

        return userCrudRespoitory.save(user).toModel();
    }

    @Override
    @CacheEvict(value = "users",key = "#id")
    public void deleteUser(UUID id) {
        userCrudRespoitory.deleteByPublicId(id);
    }

    @Override
    public String loginAs(UUID id) {
        throw new IllegalStateException("Admin login not supported for public access");

    }

    @Override
    public Page<UserModel> getAllUsers(Pageable pageable) {
        throw new IllegalStateException("Get All UserIds not allowed for public access");
    }
}
