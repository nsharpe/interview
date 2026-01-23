package org.example.media.player.controller;

import com.example.avro.media.player.MediaEvent;
import com.example.avro.media.player.MediaStart;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaStartRequest {

    @NotNull
    private long startPosition;
    @NotNull
    private UUID eventId;
    @NotNull
    private OffsetDateTime timestamp;

    @JsonProperty(defaultValue="0")
    private long mediaTimeStampMs = 0;

    private UUID lastActionId;

    @JsonIgnore
    public MediaStart toMediaStart(UUID mediaId) {
        return MediaStart.newBuilder()
                .setMediaEvent(
                        MediaEvent.newBuilder()
                                .setMediaId(mediaId)
                                .setUserId(AuthenticationInfo.get().getUserId())
                                .setTimestamp(timestamp.toInstant())
                                .setMediaTimestampMs(mediaTimeStampMs)
                                .build())
                .setLastActionId(lastActionId)
                .build();
    }
}
