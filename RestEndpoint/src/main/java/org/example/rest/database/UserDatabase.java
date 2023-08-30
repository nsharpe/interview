package org.example.rest.database;

import org.example.mysql.database.UserMysqlRepository;
import org.example.mysql.schema.UserMysql;
import org.example.repository.UserRepository;
import org.example.rest.exceptions.NotFoundException;
import org.example.service.UserModel;

public class UserDatabase implements UserRepository {

    private final UserMysqlRepository mysqlRepository;

    public UserDatabase(UserMysqlRepository mysqlRepository) {
        this.mysqlRepository = mysqlRepository;
    }

    @Override
    public UserModel getUser(int i) {
        return  mysqlRepository.findById(i)
                .orElseThrow( () -> new NotFoundException("User " + i +" not found"))
                .toModel();
    }

    @Override
    public UserModel createUser(UserModel model) {
        return mysqlRepository.save(new UserMysql(model)).toModel();
    }

    @Override
    public void deleteUser(int id) {
        mysqlRepository.deleteById(id);
    }
}
