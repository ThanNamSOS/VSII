package com.vsii;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEncryptableProperties
@EnableScheduling
public class ClaimE2ioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaimE2ioApplication.class, args);
    }

}
