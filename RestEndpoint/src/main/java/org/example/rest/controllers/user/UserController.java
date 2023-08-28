package org.example.rest.controllers.user;

import org.example.rest.models.RestUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController()
public class UserController {

    @GetMapping("/user")
    public RestUser getUser(){

        RestUser toReturn = new RestUser();

        toReturn.setEmail("example@example.com");
        toReturn.setId(1);
        toReturn.setCreatedOn(LocalDateTime.now());
        return toReturn;
    }
}
