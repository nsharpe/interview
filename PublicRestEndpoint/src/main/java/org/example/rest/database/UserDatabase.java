package org.example.rest.database;

import org.example.mysql.database.UserMysqlRepository;
import org.example.mysql.schema.UserMysql;
import org.example.repository.UserRepository;
import org.example.rest.exceptions.NotFoundException;
import org.example.service.UpdateUserModel;
import org.example.service.UserModel;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public class UserDatabase implements UserRepository {

    private final UserMysqlRepository mysqlRepository;

    public UserDatabase(UserMysqlRepository mysqlRepository) {
        this.mysqlRepository = mysqlRepository;
    }

    @Override
    @Cacheable(value = "users",key = "#id")
    public UserModel getUser(long id) {
        return  mysqlRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("User " + id +" not found"))
                .toModel();
    }

    @Override
    @CacheEvict(value = "users",key = "#result.getId()")
    public UserModel createUser(UserModel model) {
        return mysqlRepository.save(UserMysql.of(model)).toModel();
    }

    @Override
    @CachePut(value = "users",key = "#id")
    public UserModel updateUser(long id, UpdateUserModel updateUserModel) {
        UserMysql mysqlView = mysqlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User " + id +" not found"));

        mysqlView.update(updateUserModel);

        return mysqlRepository.save(mysqlView).toModel();
    }

    @Override
    @CacheEvict(value = "users",key = "#id")
    public void deleteUser(long id) {
        mysqlRepository.deleteById(id);
    }
}
