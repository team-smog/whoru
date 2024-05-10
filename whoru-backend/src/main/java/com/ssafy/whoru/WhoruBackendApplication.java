package com.ssafy.whoru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WhoruBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhoruBackendApplication.class, args);
    }

}
