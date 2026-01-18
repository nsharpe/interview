package org.example.integration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class TimeTestUtil {
    private static Duration LAST_2_SECONDS = Duration.ofSeconds(2);

    public static boolean inLast2SecondsParse(String actual){
        return inLast2Seconds(OffsetDateTime.parse(actual));
    }

    public static boolean inLast2Seconds(OffsetDateTime actual){
        return timeInThreshold(OffsetDateTime.now(),actual,LAST_2_SECONDS);
    }

    public static boolean timeInThreshold(OffsetDateTime expected, OffsetDateTime actual, Duration duration){
        if(expected.isAfter(actual)){
            return Duration.between(actual,expected).compareTo(duration) <= 0;
        }
        return Duration.between(expected,actual).compareTo(duration) <= 0;
    }
}
