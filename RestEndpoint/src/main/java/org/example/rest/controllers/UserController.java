package org.example.rest.controllers;

import org.example.service.UserModel;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = Objects.requireNonNull(userService);
    }

    @GetMapping("/user/{id}")
    public @ResponseBody UserModel getUser(@PathVariable("id")int id){

        return userService.getUser(id);
    }

    @PostMapping("/user")
    public UserModel getUser(@RequestBody UserModel userModel){
        return userService.createUser(userModel);
    }
}
