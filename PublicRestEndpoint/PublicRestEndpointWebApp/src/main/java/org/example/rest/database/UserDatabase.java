package org.example.rest.database;

import lombok.RequiredArgsConstructor;
import org.example.core.exceptions.NotFoundException;

import org.example.users.UpdateUserModel;
import org.example.users.UserModel;
import org.example.users.UserRepository;
import org.example.users.repository.UserPostgres;
import org.example.users.repository.UserCrudRespoitory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<UUID> getAllUserIds() {
        throw new IllegalStateException("Get All UserIds not allowed for public access");
    }
}
