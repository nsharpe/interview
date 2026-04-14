package org.amoeba.example.app.media.comment;

import org.springframework.boot.SpringApplication;import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.amoeba.example"})
public class MediaCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaCommentApplication.class);
    }
}