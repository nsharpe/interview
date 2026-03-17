package org.example.metric.media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.example"})
public class MediaMetricEndpointWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaMetricEndpointWebappApplication.class, args);
    }

}
