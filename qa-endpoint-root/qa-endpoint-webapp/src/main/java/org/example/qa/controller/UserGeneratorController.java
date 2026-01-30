package org.example.qa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.publicrest.sdk.api.UserControllerApi;
import org.example.publicrest.sdk.models.UserModel;
import org.example.test.data.UserGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/generator/user")
@Slf4j
public class UserGeneratorController {

    @Autowired
    @Lazy
    private UserGenerator userGenerator;

    @Operation(summary = "Generate a user",
            responses = {
                    @ApiResponse(description = "User ids created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(type = "string", format = "uuid"))
                            ))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<UUID> generate(@RequestParam("count") int count){
        return IntStream.range(0,count)
                .mapToObj(x -> userGenerator.generate())
                .map(UserModel::getId)
                .peek(x->log.atInfo()
                        .addKeyValue("userId",x)
                        .log("Created User")
                )
                .toList();
    }
}
