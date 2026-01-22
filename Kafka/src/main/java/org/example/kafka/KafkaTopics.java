package org.example.kafka;

public class KafkaTopics {

    private KafkaTopics(){
        // NOOP
    }

    public static final String MEDIA_ENGAGEMENT = "media.player.engagement";
    public static final String MEDIA_START = "media.player.start";
    public static final String MEDIA_END = "media.player.end";
    public static final String MEDIA_REPOSITION = "media.player.reposition";
}
