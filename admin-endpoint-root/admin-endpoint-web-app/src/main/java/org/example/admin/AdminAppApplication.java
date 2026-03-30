package org.example.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication(scanBasePackages = {"org.example"})
public class AdminAppApplication {

    public static void main(String[] args){
        SpringApplication.run(AdminAppApplication.class);
    }

}
