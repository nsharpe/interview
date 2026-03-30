package org.example.integration.util;

import java.time.Duration;
import java.time.OffsetDateTime;

public class TimeTestUtil {
    private static final Duration LAST_5_SECONDS = Duration.ofSeconds(5);

    public static boolean inLast5SecondsParse(String actual){
        return inLast5Seconds(OffsetDateTime.parse(actual));
    }

    public static boolean inLast5Seconds(OffsetDateTime actual){
        return timeInThreshold(OffsetDateTime.now(),actual, LAST_5_SECONDS);
    }

    public static boolean timeInThreshold(OffsetDateTime expected,
                                          OffsetDateTime actual,
                                          Duration duration){
        if(expected.isAfter(actual)){
            return Duration.between(actual,expected).compareTo(duration) <= 0;
        }
        return Duration.between(expected,actual).compareTo(duration) <= 0;
    }
}
