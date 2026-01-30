package org.example.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.example")
public class QaApplication {
    public static void main(String[] args){
        SpringApplication.run(QaApplication.class);
    }
}
