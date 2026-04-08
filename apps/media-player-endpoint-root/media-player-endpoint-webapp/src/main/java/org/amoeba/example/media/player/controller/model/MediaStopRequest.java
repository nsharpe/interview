package org.amoeba.example.media.player.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.amoeba.example.avro.media.player.MediaStop;

import java.util.UUID;

/**
 * A user has stopped consuming a piece of media.  This can happen when a user completes the media, or when a user leaves the page a media is being played on.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaStopRequest {

    @NotNull
    private MediaPlayerEventBase eventState;

    @NotNull
    private UUID lastActionId;

    @JsonIgnore
    public MediaStop toMediaStop() {
        return MediaStop.newBuilder()
                .setMediaEvent(eventState.toMediaEvent())
                .setLastActionId(lastActionId)
                .build();
    }
}
