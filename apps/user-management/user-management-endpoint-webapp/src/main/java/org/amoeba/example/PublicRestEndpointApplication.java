package org.amoeba.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.amoeba.example"})
public class PublicRestEndpointApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublicRestEndpointApplication.class,args);
    }
}