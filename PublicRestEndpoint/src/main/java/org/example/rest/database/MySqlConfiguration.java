package org.example.rest.database;

import org.example.mysql.database.UserMysqlRepository;
import org.example.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySqlConfiguration {

    @Bean
    public UserRepository userRepository(UserMysqlRepository userMysqlRepository){
        return new UserDatabase(userMysqlRepository);
    }
}
