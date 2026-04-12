package org.amoeba.example.util.webflux;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class FluxStreamPollTest {

    private FluxStreamPoll<String, String> fluxStreamPoll;

    @BeforeEach
    void setUp() {
        fluxStreamPoll = FluxStreamPoll.<String, String>builder()
                .duration(Duration.ofSeconds(1))
                .source(input -> Flux.just("value-" + input))
                .build();
    }

    @Test
    void testActiveStreamsRemovalWhenCancelled() {
        // Test that the input is removed from active streams when cancelled
        Flux<String> mockSource = Flux.just("test-value");

        fluxStreamPoll = FluxStreamPoll.<String, String>builder()
                .duration(Duration.ofSeconds(1))
                .source(input -> mockSource)
                .build();

        // Get the shared stream
        Flux<String> stream = fluxStreamPoll.getSharedCountStream("test-input");

        // Verify that the input is in active streams before subscription
        assertTrue(fluxStreamPoll.getActiveStreams().containsKey("test-input"),
            "Input should be in active streams before subscription");

        // Subscribe to the stream and then cancel it immediately
        Disposable disposable = stream.subscribe();

        // Cancel the subscription
        disposable.dispose();

        waitTillEmpty();
        // Verify that the input is removed from active streams after cancellation
        assertFalse(fluxStreamPoll.getActiveStreams().containsKey("test-input"),
            "Input should be removed from active streams after cancellation");
    }

    @Test
    void testMultipleSubscriptionsAndCancellation() {
        // Test behavior with multiple subscriptions to same input
        Flux<String> mockSource = Flux.just("test-value");

        fluxStreamPoll = FluxStreamPoll.<String, String>builder()
                .duration(Duration.ofSeconds(1))
                .source(input -> mockSource)
                .build();

        // Get the shared stream
        Flux<String> stream = fluxStreamPoll.getSharedCountStream("test-input");

        // Subscribe twice to the same stream
        Disposable disposable1 = stream.subscribe();
        Disposable disposable2 = stream.subscribe();

        // Verify that the input is in active streams
        assertTrue(fluxStreamPoll.getActiveStreams().containsKey("test-input"),
            "Input should be in active streams");

        // Cancel one subscription
        disposable1.dispose();

        // Input should still be in active streams since second subscription exists
        assertTrue(fluxStreamPoll.getActiveStreams().containsKey("test-input"),
            "Input should still be in active streams after one cancellation");

        // Cancel the second subscription
        disposable2.dispose();


        waitTillEmpty();
        // Input should now be removed from active streams
        assertFalse(fluxStreamPoll.getActiveStreams().containsKey("test-input"),
            "Input should be removed from active streams after all cancellations");
    }

    @Test
    void testDoOnCancelIsCalledWhenStreamIsCancelled() {
        // Test that verifies the doOnCancel functionality by checking if
        // the stream is properly removed from activeStreams when cancelled

        // Create a mock source that we can track
        AtomicBoolean cancelled = new AtomicBoolean(false);

        Flux<String> mockSource = Flux.just("test-value")
                .doOnCancel(() -> cancelled.set(true));

        fluxStreamPoll = FluxStreamPoll.<String, String>builder()
                .duration(Duration.ofSeconds(1))
                .source(input -> mockSource)
                .build();

        // Get the shared stream
        Flux<String> stream = fluxStreamPoll.getSharedCountStream("test-input");

        // Subscribe to the stream
        Disposable disposable = stream.subscribe();

        // Cancel the subscription
        disposable.dispose();

        waitTillEmpty();
        // Verify that the input is removed from active streams after cancellation
        assertFalse(fluxStreamPoll.getActiveStreams().containsKey("test-input"),
            "Input should be removed from active streams after cancellation");
    }

    private void waitTillEmpty(){
        Awaitility.await().atMost(Duration.ofMillis(500))
                .until(() -> fluxStreamPoll.getActiveStreams().isEmpty());
    }
}