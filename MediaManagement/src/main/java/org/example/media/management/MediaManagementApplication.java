package org.example.media.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication(scanBasePackages = {"org.example"})
public class MediaManagementApplication {
    public static void main(String[] args){
        SpringApplication.run(MediaManagementApplication.class,args);
    }
}