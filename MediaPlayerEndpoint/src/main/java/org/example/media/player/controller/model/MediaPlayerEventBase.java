package org.example.media.player.controller.model;

import com.example.avro.media.player.MediaEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.security.AuthenticationInfo;

import java.time.OffsetDateTime;
import java.util.UUID;

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
                .setUserId(AuthenticationInfo.get().getUserId())
                .setTimestamp(timestamp.toInstant())
                .setMediaTimestampMs(mediaPosition)
                .build();
    }
}
