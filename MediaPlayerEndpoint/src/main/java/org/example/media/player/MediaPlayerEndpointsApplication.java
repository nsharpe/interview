package org.example.media.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication(scanBasePackages = {"org.example"})
public class MediaPlayerEndpointsApplication {
    public static void main(String[] args){
        SpringApplication.run(MediaPlayerEndpointsApplication.class,args);
    }
}