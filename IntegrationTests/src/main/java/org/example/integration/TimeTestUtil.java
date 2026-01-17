package org.example.integration;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeTestUtil {
    private static Duration LAST_2_SECONDS = Duration.ofSeconds(2);

    public static boolean inLast2SecondsParse(String actual){
        return inLast2Seconds(LocalDateTime.parse(actual));
    }

    public static boolean inLast2Seconds(LocalDateTime actual){
        return timeInThreshold(LocalDateTime.now(),actual,LAST_2_SECONDS);
    }

    public static boolean timeInThreshold(LocalDateTime expected, LocalDateTime actual, Duration duration){
        if(expected.isAfter(actual)){
            return Duration.between(actual,expected).compareTo(duration) <= 0;
        }
        return Duration.between(expected,actual).compareTo(duration) <= 0;
    }
}
