package org.example.media.player.controller;

import com.example.avro.media.MediaStart;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public MediaStart toMediaStart(UUID mediaId){
        return MediaStart.newBuilder()
                .setMediaId(mediaId)
                .setUserId(AuthenticationInfo.get().getUserId())
                .setTimestamp(timestamp.toInstant())
                .build();
    }
}
