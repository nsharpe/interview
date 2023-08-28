package org.example.rest;

import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration
{
    @Bean
    public UserService userService(UserRepository repository){
        return new UserService(repository);
    }

}
