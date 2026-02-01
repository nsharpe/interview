package org.example.qa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.media.management.sdk.models.SeriesModel;
import org.example.publicrest.sdk.models.UserModel;
import org.example.test.data.MovieGenerator;
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
@RequestMapping("/generator/movies")
@Slf4j
public class SeriesGeneratorController {

    @Autowired
    @Lazy
    private MovieGenerator movieGenerator;

    @Operation(summary = "Generate a movie",
            responses = {
                    @ApiResponse(description = "Movie ids created",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(type = "string", format = "uuid"))
                            ))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<UUID> movieGenerate(@RequestParam("count") int count){
        return IntStream.range(0,count)
                .mapToObj(x -> movieGenerator.generate())
                .map(SeriesModel::getId)
                .peek(x->log.atInfo()
                        .addKeyValue("series",x)
                        .log("Created Movie")
                )
                .toList();
    }
}
