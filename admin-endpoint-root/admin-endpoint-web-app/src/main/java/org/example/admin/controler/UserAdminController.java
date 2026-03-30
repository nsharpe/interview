package org.example.admin.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.admin.config.openapi.UserModelPage;
import org.example.publicrest.sdk.api.UserControllerApi;
import org.example.users.UserModel;
import org.example.users.UserRepository;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserControllerApi userControllerApi;

    @Operation(summary = "Get All users",
            responses = {
                    @ApiResponse(description = "Gets the ids of all users that are not soft deleted",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserModelPage.class)
                            ))})
    @GetMapping
    public @ResponseBody Page<UserModel> getUsers(@ParameterObject Pageable pageable){
        return userRepository.getAllUsers(pageable);
    }

    @PostMapping("/{id}/loginas")
    public @ResponseBody AdminAuthorization loginAsUser(@PathVariable("id") UUID userId){
        return userControllerApi.getUser(userId)
                .map(x->userRepository.loginAs(userId))
                .map(AdminAuthorization::new)
                .block();
    }

    public record AdminAuthorization(String token){
    }
}
