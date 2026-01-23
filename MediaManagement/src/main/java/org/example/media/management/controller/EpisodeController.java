package org.example.media.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.example.core.exceptions.NotFoundException;
import org.example.series.episode.EpisodeModel;
import org.example.series.episode.EpisodeService;
import org.example.series.episode.EpisodeCreateModel;
import org.example.series.episode.EpisodeUpdateModel;
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

import java.util.UUID;

@RestController
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @Operation(summary = "Get episode by id",
            responses = {
                    @ApiResponse(description = "The Episode",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EpisodeModel.class))),
                    @ApiResponse(responseCode = "404", description = "episode not found")})
    @GetMapping("/series/{seriesId}/season/{seasonId}/episode/{id}")
    public @ResponseBody EpisodeModel get(@PathVariable("id") UUID id,
                                          @PathVariable("seasonId") UUID seasonId,
                                         @PathVariable("seriesId") UUID seriesId){
        EpisodeModel episodeModel = episodeService.get(id);
        if(!seriesId.equals(  episodeModel.getSeries())) {
            throw new NotFoundException("series",seriesId);
        }

        if(!seasonId.equals(  episodeModel.getSeason())) {
            throw new NotFoundException("episode",seriesId);
        }

        return episodeModel;

    }

    @Operation(summary = "Create a episode",
            responses = {
                    @ApiResponse(description = "The episode",
                            responseCode = "204",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EpisodeModel.class)))})
    @PostMapping("/series/{seriesId}/season/{seasonId}/episode")
    @ResponseStatus(HttpStatus.CREATED)
    public EpisodeModel create(@RequestBody @Valid EpisodeCreateModel episodeCreateModel,
                               @PathVariable("seasonId") UUID seasonId,
                              @PathVariable("seriesId") UUID seriesId){
        return episodeService.create(episodeCreateModel,seasonId,seriesId);
    }

    @Operation(summary = "Modify a episode",
            responses = {
                    @ApiResponse(description = "The Episode after modification",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EpisodeModel.class))),
                    @ApiResponse(responseCode = "404", description = "episode not found")})
    @PutMapping("/series/{seriesId}/season/{seasonId}/episode/{id}")
    public EpisodeModel modify(@PathVariable("id") UUID id,
                               @PathVariable("seasonId") UUID seasonId,
                              @PathVariable("seriesId") UUID seriesId,
                              @RequestBody EpisodeUpdateModel episodeModel){
        return episodeService.update( episodeModel, id,seasonId, seriesId);
    }

    @Operation(summary = "Delete a Episode",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Episode deleted")})
    @DeleteMapping("/series/{seriesId}/season/{seasonId}/episode/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")UUID id,
                       @PathVariable("seasonId") UUID seasonId,
                       @PathVariable("seriesId") UUID seriesId){
        episodeService.delete(id,seasonId,seriesId);
    }
}
