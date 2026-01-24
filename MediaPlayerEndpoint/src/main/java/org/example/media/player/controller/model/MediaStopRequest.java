package org.example.media.player.controller.model;

import com.example.avro.media.player.MediaStop;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


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
