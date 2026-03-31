package org.amoeba.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.amoeba.example")
public class TestApplication {

    public static void main(String[] args){
        SpringApplication.run(TestApplication.class,args);
    }
}
