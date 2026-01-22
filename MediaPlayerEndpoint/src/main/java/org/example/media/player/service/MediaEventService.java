package org.example.media.player.service;

import com.example.avro.media.MediaEngagement;
import com.example.avro.media.MediaPlayerEventKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static org.example.kafka.KafkaTopics.MEDIA_ENGAGEMENT;

@Service
@Slf4j
@Profile("!openapi")
public class MediaEventService {

    private final KafkaTemplate<MediaPlayerEventKey, MediaEngagement> kafkaTemplate;

    public MediaEventService(KafkaTemplate<MediaPlayerEventKey, MediaEngagement> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MediaPlayerEventKey key, MediaEngagement data) {

        CompletableFuture<SendResult<MediaPlayerEventKey, MediaEngagement>> future =
                kafkaTemplate.send(MEDIA_ENGAGEMENT, key, data);

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
