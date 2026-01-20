package org.example.media.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.example.series.SeriesCreateModel;
import org.example.series.SeriesModel;
import org.example.series.SeriesService;
import org.example.series.SeriesUpdateModel;
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
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = Objects.requireNonNull(seriesService);
    }

    @Operation(summary = "Get series by id",
            responses = {
                    @ApiResponse(description = "The series",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SeriesModel.class))),
                    @ApiResponse(responseCode = "404", description = "Series not found")})
    @GetMapping("/series/{id}")
    public @ResponseBody SeriesModel get(@PathVariable("id")UUID id){
        return seriesService.getSeries(id);
    }

    @Operation(summary = "Create a Series",
            responses = {
                    @ApiResponse(description = "The series",
                            responseCode = "204",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SeriesCreateModel.class)))})
    @PostMapping("/series")
    @ResponseStatus(HttpStatus.CREATED)
    public SeriesModel create(@RequestBody @Valid SeriesCreateModel seriesModel){
        return seriesService.createSeries(seriesModel);
    }

    @Operation(summary = "Modify a Series",
            responses = {
                    @ApiResponse(description = "The Series after modification",
                            responseCode = "204",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SeriesUpdateModel.class))),
                    @ApiResponse(responseCode = "404", description = "Series not found")})
    @PutMapping("/series/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SeriesModel modify(@PathVariable("id") UUID id, @RequestBody SeriesUpdateModel seriesModel){
        return seriesService.updateSeries( seriesModel, id);
    }

    @Operation(summary = "Delete a Series",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Series deleted")})
    @DeleteMapping("/series/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")UUID id){
        seriesService.deleteSeries(id);
    }
}
