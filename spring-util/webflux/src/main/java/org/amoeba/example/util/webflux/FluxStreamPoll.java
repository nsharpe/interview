package org.amoeba.example.util.webflux;

import lombok.Builder;
import lombok.NonNull;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


/**
 * Creates a factory for a flux where subscriptions occur based on a common input.
 *
 * This was designed for calls periodic updates are required for client facing updates.
 *
 * @param <I>
 * @param <O>
 */
@Builder
public class FluxStreamPoll<I,O>{

    private final Map<I, Flux<O>> activeStreams = new ConcurrentHashMap<>();

    @NonNull
    private final Duration duration;
    @NonNull
    private final Function<I,Flux<O>> source;

    private int replay;

    public Flux<O> getSharedCountStream(I input) {
        return activeStreams.computeIfAbsent(input, this::createBroadcastStream);
    }

    private Flux<O> createBroadcastStream(I input) {
        return Flux.interval(duration)
                .flatMap(tick -> source.apply(input))
                .distinctUntilChanged()
                .doFinally(signalType -> activeStreams.remove(input))
                .replay(replay) // Send the last known value immediately to new subscribers
                .refCount(1);
    }

    // For testing purposes only
    Map<I, Flux<O>> getActiveStreams() {
        return activeStreams;
    }
}
