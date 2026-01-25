package org.example.rest.database;

import org.example.users.UserRepository;
import org.example.users.repository.UserCrudRespoitory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {


    @Bean
    public UserRepository userRepository(UserCrudRespoitory userCrudRespoitory) {
        return new UserDatabase(userCrudRespoitory);
    }
}
