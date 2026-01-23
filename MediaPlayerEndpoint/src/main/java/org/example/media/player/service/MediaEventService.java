package org.example.media.player.service;

import com.example.avro.media.player.MediaPlayerEventKey;
import com.example.avro.media.player.MediaStart;
import lombok.extern.slf4j.Slf4j;
import org.example.media.player.controller.MediaStartRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.example.kafka.KafkaTopics.MEDIA_ENGAGEMENT;


@Service
@Slf4j
public class MediaEventService {

    private final KafkaTemplate<MediaPlayerEventKey, MediaStart> kafkaTemplate;

    public MediaEventService(KafkaTemplate<MediaPlayerEventKey, MediaStart> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void startMedia(MediaStartRequest mediaStartRequest, UUID mediaId) {

        CompletableFuture<SendResult<MediaPlayerEventKey, MediaStart>> future =
                kafkaTemplate.send(MEDIA_ENGAGEMENT, MediaPlayerEventKey.newBuilder()
                                .setEventId(mediaStartRequest.getEventId())
                                .build(),
                        mediaStartRequest.toMediaStart(mediaId)
                );

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.atError()
                        .addKeyValue("topic",MEDIA_ENGAGEMENT)
                        .setCause(ex)
                        .log();
            }
        });
    }
}
