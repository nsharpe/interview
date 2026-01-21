package org.example.media.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.example.exceptions.NotFoundException;
import org.example.series.season.SeasonCreateModel;
import org.example.series.season.SeasonModel;
import org.example.series.season.SeasonService;
import org.example.series.season.SeasonUpdateModel;
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
public class SeasonController {

    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService) {
        this.seasonService = Objects.requireNonNull(seasonService);
    }

    @Operation(summary = "Get season by id",
            responses = {
                    @ApiResponse(description = "The Season",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SeasonModel.class))),
                    @ApiResponse(responseCode = "404", description = "season not found")})
    @GetMapping("/series/{seriesId}/season/{id}")
    public @ResponseBody SeasonModel get(@PathVariable("id") UUID id,
                                         @PathVariable("seriesId") UUID seriesId){
        SeasonModel seasonModel = seasonService.getSeason(id);
        if(seriesId.equals(  seasonModel.getSeriesId())) {
            return seasonModel;
        }

        throw new NotFoundException("series",seriesId);
    }

    @Operation(summary = "Create a season",
            responses = {
                    @ApiResponse(description = "The season",
                            responseCode = "204",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SeasonCreateModel.class)))})
    @PostMapping("/series/{seriesId}/season")
    @ResponseStatus(HttpStatus.CREATED)
    public SeasonModel create(@RequestBody @Valid SeasonCreateModel seasonCreateModel,
                                 @PathVariable("seriesId") UUID seriesId){
        return seasonService.create(seasonCreateModel,seriesId);
    }

    @Operation(summary = "Modify a season",
            responses = {
                    @ApiResponse(description = "The Season after modification",
                            responseCode = "204",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SeasonUpdateModel.class))),
                    @ApiResponse(responseCode = "404", description = "season not found")})
    @PutMapping("/series/{seriesId}/season/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SeasonModel modify(@PathVariable("id") UUID id,
                              @PathVariable("seriesId") UUID seriesId,
                              @RequestBody SeasonUpdateModel seasonModel){
        return seasonService.updateSeason( seasonModel, id, seriesId);
    }

    @Operation(summary = "Delete a Season",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Season deleted")})
    @DeleteMapping("/series/{seriesId}/season/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")UUID id,
                           @PathVariable("seriesId") UUID seriesId){
        seasonService.deleteSeason(id);
    }
}
