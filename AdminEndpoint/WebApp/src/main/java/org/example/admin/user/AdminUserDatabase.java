package org.example.admin.user;

import lombok.RequiredArgsConstructor;
import org.example.core.exceptions.NotFoundException;
import org.example.core.model.AuthenticationInfo;
import org.example.users.UpdateUserModel;
import org.example.users.UserModel;
import org.example.users.UserRepository;
import org.example.users.repository.UserCrudRespoitory;
import org.example.users.repository.UserPostgres;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserDatabase implements UserRepository {

    private final UserCrudRespoitory userCrudRespoitory;
    private final RedisTemplate<String, AuthenticationInfo> redisTemplate;

    @Override
    public UserModel getUser(long id) {
        return  userCrudRespoitory.findById(id)
                .orElseThrow( () -> new NotFoundException("user",id) )
                .toModel();
    }

    @Override
    public UserModel getUser(UUID publicId) {
        return  userCrudRespoitory.findByPublicId(publicId)
                .orElseThrow( () -> new NotFoundException("user", publicId))
                .toModel();
    }

    @Override
    public UserModel createUser(UserModel model) {
        return userCrudRespoitory.save(UserPostgres.of(model)).toModel();
    }

    @Override
    public UserModel updateUser(UUID id, UpdateUserModel updateUserModel) {
        UserPostgres user = userCrudRespoitory.findByPublicId(id)
                .orElseThrow(() -> new NotFoundException("User " + id +" not found"));

        user.update(updateUserModel);

        return userCrudRespoitory.save(user).toModel();
    }

    @Override
    public void deleteUser(UUID id) {
        userCrudRespoitory.deleteByPublicId(id);
    }

    @Override
    public String loginAs(UUID id) {
        AuthenticationInfo authenticationInfo = userCrudRespoitory.findByPublicId(id)
                .map(AdminUserDatabase::authenticationInfoOf)
                .orElseThrow(()-> new NotFoundException("userId",id));

        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("Bearer "+token, authenticationInfo, Duration.ofDays(365));

        return token;

    }

    private static AuthenticationInfo authenticationInfoOf(UserPostgres user){
        return AuthenticationInfo.builder()
                .userId(user.getPublicId())
                .roles(List.of("SUBSCRIBER"))
                .build();
    }
}
