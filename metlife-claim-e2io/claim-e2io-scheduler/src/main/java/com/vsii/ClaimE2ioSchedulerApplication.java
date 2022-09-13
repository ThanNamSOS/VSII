package com.vsii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClaimE2ioSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaimE2ioSchedulerApplication.class, args);
    }

}
