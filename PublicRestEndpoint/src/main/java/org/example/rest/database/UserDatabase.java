package org.example.rest.database;

import org.example.core.exceptions.NotFoundException;

import org.example.users.UpdateUserModel;
import org.example.users.UserModel;
import org.example.users.UserRepository;
import org.example.users.repository.UserPostgres;
import org.example.users.repository.UserCrudRespoitory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.UUID;

public class UserDatabase implements UserRepository {

    private final UserCrudRespoitory userCrudRespoitory;

    public UserDatabase(UserCrudRespoitory userCrudRespoitory) {
        this.userCrudRespoitory = userCrudRespoitory;
    }

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
}
