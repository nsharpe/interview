package org.example.media.player.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.example.media.player.controller.model.MediaStartRequest;
import org.example.media.player.controller.model.MediaStopRequest;
import org.example.media.player.service.MediaEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/player")
public class MediaEventController {

    @Autowired
    private MediaEventService mediaEventService;

    @Operation(summary = "Start tracking media play",
            responses = {
                    @ApiResponse(description = "The episode",
                            responseCode = "202",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MediaEventResponse.class)))})
    @PostMapping("/media/{mediaId}/start")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MediaEventResponse start(@RequestBody @Valid MediaStartRequest mediaStartRequest,
                                    @PathVariable("mediaId") UUID mediaId) throws ExecutionException, InterruptedException {
        mediaEventService.startMedia(mediaStartRequest,mediaId);

        return MediaEventResponse.builder()
                .actionId(mediaStartRequest.getEventState().getEventId())
                .build();
    }

    @Operation(summary = "Signals that a media is no longer being viewed",
            responses = {
                    @ApiResponse(description = "The episode",
                            responseCode = "202",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MediaEventResponse.class)))})
    @PostMapping("/media/stop")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MediaEventResponse stop(@RequestBody @Valid MediaStopRequest mediaStopRequest) throws ExecutionException, InterruptedException {
        mediaEventService.stopMedia(mediaStopRequest);

        return MediaEventResponse.builder()
                .actionId(mediaStopRequest.getEventState().getEventId())
                .build();
    }
}
