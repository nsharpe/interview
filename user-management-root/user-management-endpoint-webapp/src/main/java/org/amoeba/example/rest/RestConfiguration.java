package org.amoeba.example.rest;

import org.amoeba.example.users.UserRepository;
import org.amoeba.example.users.UserService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RestConfiguration
{
    @Bean
    public UserService userService(UserRepository repository){
        return new UserService(repository);
    }
}
