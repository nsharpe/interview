package org.example.media.player.controller;

import com.example.avro.media.player.MediaStart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.media.player.controller.model.MediaPlayerEventBase;

import java.util.UUID;


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
