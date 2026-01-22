package org.example.media.player.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MediaEvent {

    private OffsetDateTime eventSent;
    private long mediaPosition;
    private UUID eventId;
    private UUID lastActionId;

}
