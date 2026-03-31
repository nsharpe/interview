package org.amoeba.example.media.player.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.amoeba.example.avro.media.player.MediaEvent;
import org.amoeba.example.security.SecurityConfiguration;

import java.time.OffsetDateTime;
import java.util.UUID;


/**
 * This class is a pojo of all information each media event must satisfy
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaPlayerEventBase {

    @NotNull
    private long mediaPosition;
    @NotNull
    private UUID eventId;
    @NotNull
    private OffsetDateTime timestamp;

    public MediaEvent toMediaEvent(){
        return MediaEvent.newBuilder()
                .setUserId(SecurityConfiguration.getAuthenticationInfo().getUserId())
                .setTimestamp(timestamp.toInstant())
                .setMediaTimestampMs(mediaPosition)
                .build();
    }
}
