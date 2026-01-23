package org.example.media.player.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.example.media.player.service.MediaEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MediaEventController {

    @Autowired
    private MediaEventService mediaEventService;

    @Operation(summary = "Start tracking media play",
            responses = {
                    @ApiResponse(description = "The episode",
                            responseCode = "202",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MediaStartRequest.class)))})
    @PostMapping("/media/{mediaId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MediaStartResponse start(@RequestBody @Valid MediaStartRequest mediaStartRequest,
                               @PathVariable("mediaId") UUID mediaId){
        mediaEventService.startMedia(mediaStartRequest,mediaId);

        return MediaStartResponse.builder()
                .actionId(mediaStartRequest.getEventId())
                .build();
    }
}
