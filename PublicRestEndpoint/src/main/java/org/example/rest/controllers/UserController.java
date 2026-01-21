package org.example.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.example.rest.controllers.user.CreateUserModel;

import org.example.users.UpdateUserModel;
import org.example.users.UserModel;
import org.example.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = Objects.requireNonNull(userService);
    }

    @Operation(summary = "Get user by id",
            responses = {
                    @ApiResponse(description = "The user",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserModel.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/user/{id}")
    public @ResponseBody UserModel getUser(@PathVariable("id") UUID id){
        return userService.getUser(id);
    }

    @Operation(summary = "Create a User",
            responses = {
                    @ApiResponse(description = "The user",
                            responseCode = "204",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreateUserModel.class))),
                    // TODO: Fix status code
                    @ApiResponse(responseCode = "500", description = "Email already exists")})
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel create(@RequestBody @Valid CreateUserModel userModel){
        return userService.createUser(userModel.toUserModel());
    }

    @Operation(summary = "Modify a User",
            responses = {
                    @ApiResponse(description = "The user after modification",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateUserModel.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")})
    @PutMapping("/user/{id}")
    public UserModel modify(@PathVariable("id")UUID id, @RequestBody UpdateUserModel userModel){
        return userService.updateUser(id, userModel);
    }

    @Operation(summary = "Delete a User",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted")})
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id")UUID id){
        userService.deleteUser(id);
    }
}
