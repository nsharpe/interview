package org.example.media.player.service;

import com.example.avro.media.player.MediaPlayerEventKey;
import com.example.avro.media.player.MediaStart;
import com.example.avro.media.player.MediaStop;
import lombok.extern.slf4j.Slf4j;
import org.example.media.player.controller.model.MediaStartRequest;
import org.example.media.player.controller.model.MediaStopRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.example.kafka.KafkaTopics.*;


@Service
@Slf4j
public class MediaEventService {

    private final KafkaTemplate<MediaPlayerEventKey, MediaStart> startTemplate;
    private final KafkaTemplate<MediaPlayerEventKey, MediaStop> stopTemplate;

    public MediaEventService(KafkaTemplate<MediaPlayerEventKey, MediaStart> startTemplate,
                             KafkaTemplate<MediaPlayerEventKey, MediaStop> stopTemplate) {
        this.startTemplate = Objects.requireNonNull(startTemplate);
        this.stopTemplate = Objects.requireNonNull(stopTemplate);
    }

    public void startMedia(MediaStartRequest mediaStartRequest, UUID mediaId) throws ExecutionException, InterruptedException {

        CompletableFuture<SendResult<MediaPlayerEventKey, MediaStart>> future =
                startTemplate.send(MEDIA_START, MediaPlayerEventKey.newBuilder()
                                .setEventId(mediaStartRequest.getEventState().getEventId())
                                .build(),
                        mediaStartRequest.toMediaStart(mediaId)
                );

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.atError()
                        .addKeyValue("topic",MEDIA_START)
                        .setCause(ex)
                        .log("");
            }
        })
                .get();
    }

    public void stopMedia(MediaStopRequest mediaStopRequest) throws ExecutionException, InterruptedException {

        CompletableFuture<SendResult<MediaPlayerEventKey, MediaStop>> future =
                stopTemplate.send(MEDIA_STOP, MediaPlayerEventKey.newBuilder()
                                .setEventId(mediaStopRequest.getEventState().getEventId())
                                .build(),
                        mediaStopRequest.toMediaStop()
                );

        future.whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.atError()
                                .addKeyValue("topic",MEDIA_ENGAGEMENT)
                                .setCause(ex)
                                .log("");
                    }
                })
                .get();
    }
}
