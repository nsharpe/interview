package org.amoeba.example.media.player.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.amoeba.example.avro.media.player.MediaStart;

import java.util.UUID;

/**
 * The start of a media event request chain
 *
 * Typically, this occurs when a video either autoplays for a user, or a user manually presses play on some media.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaStartRequest {
    private MediaPlayerEventBase eventState;

    private UUID lastActionId;

    @JsonIgnore
    public MediaStart toMediaStart(UUID mediaId) {
        return MediaStart.newBuilder()
                .setMediaEvent(eventState.toMediaEvent())
                .setLastActionId(lastActionId)
                .setMediaId(mediaId)
                .build();
    }
}
