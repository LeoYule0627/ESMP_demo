package com.practice.esmp_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EsmpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsmpDemoApplication.class, args);
    }

}
