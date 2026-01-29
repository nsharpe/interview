package org.example.admin.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.publicrest.sdk.api.UserControllerApi;
import org.example.users.UserRepository;
import org.example.users.repository.UserCrudRespoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserAdminController {

    @Autowired
    private UserCrudRespoitory userCrudRespoitory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserControllerApi userControllerApi;

    @Operation(summary = "Get All user ids",
            responses = {
                    @ApiResponse(description = "Gets the ids of all users that are not soft deleted",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(type = "string", format = "uuid"))
                            ))})
    @GetMapping
    public @ResponseBody List<UUID> getUsers(){
        return userRepository.getAllUserIds();
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
