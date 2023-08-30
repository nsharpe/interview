package org.example.rest.database;

import org.example.mysql.database.UserMysqlRepository;
import org.example.mysql.schema.UserMysql;
import org.example.repository.UserRepository;
import org.example.rest.exceptions.NotFoundException;
import org.example.service.UpdateUserModel;
import org.example.service.UserModel;

public class UserDatabase implements UserRepository {

    private final UserMysqlRepository mysqlRepository;

    public UserDatabase(UserMysqlRepository mysqlRepository) {
        this.mysqlRepository = mysqlRepository;
    }

    @Override
    public UserModel getUser(long id) {
        return  mysqlRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("User " + id +" not found"))
                .toModel();
    }

    @Override
    public UserModel createUser(UserModel model) {
        return mysqlRepository.save(new UserMysql(model)).toModel();
    }

    @Override
    public UserModel updateUser(long id, UpdateUserModel updateUserModel) {
        UserMysql mysqlView = mysqlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User " + id +" not found"));

        mysqlView.update(updateUserModel);

        return mysqlRepository.save(mysqlView).toModel();
    }

    @Override
    public void deleteUser(long id) {
        mysqlRepository.deleteById(id);
    }
}
