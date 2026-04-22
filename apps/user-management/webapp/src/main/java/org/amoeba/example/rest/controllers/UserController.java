package org.amoeba.example.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.amoeba.example.rest.controllers.user.CreateUserModel;

import org.amoeba.example.users.UpdateUserModel;
import org.amoeba.example.users.UserModel;
import org.amoeba.example.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
    public Mono<UserModel> getUser(@PathVariable("id") UUID id){
        return Mono.fromCallable(() ->userService.getUser(id))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Operation(summary = "Create a User",
            responses = {
                    @ApiResponse(description = "The user",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserModel.class))),
                    // TODO: Fix status code
                    @ApiResponse(responseCode = "500", description = "Email already exists")})
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserModel> create(@Valid @RequestBody Mono<CreateUserModel> userModel){
        return userModel.map(um-> userService.createUser(um.toUserModel()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Operation(summary = "Modify a User",
            responses = {
                    @ApiResponse(description = "The user after modification",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserModel.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")})
    @PutMapping("/user/{id}")
    public Mono<UserModel> modify(@PathVariable("id")UUID id, @Valid @RequestBody Mono<UpdateUserModel> userModel){
        return userModel.map(um -> userService.updateUser(id, um))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Operation(summary = "Delete a User",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted")})
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable("id")UUID id){

        return Mono.fromCallable(() -> {
                    userService.deleteUser(id);
                    return (Void)null;
                }).subscribeOn(Schedulers.boundedElastic());
    }
}
