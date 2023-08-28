package org.example.rest;

import org.example.repository.UserRepository;
import org.example.service.UserModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Profile("RestOnly")
public class RestOnlyConfiguration {

    @Bean
    public UserRepository mockUserService(){
        return new MockUserService();
    }


    private static class MockUserService implements UserRepository {

        private AtomicInteger id = new AtomicInteger(1);

        private Map<Integer,UserModel> users = new HashMap<>();

        @Override
        public UserModel getUser(int i) {
            return users.get(i);
        }

        @Override
        public UserModel createUser(UserModel model) {
            model.setId(id.getAndIncrement());
            model.setCreatedOn(LocalDateTime.now());

            users.put(model.getId(),model);

            return model;
        }
    }
}
