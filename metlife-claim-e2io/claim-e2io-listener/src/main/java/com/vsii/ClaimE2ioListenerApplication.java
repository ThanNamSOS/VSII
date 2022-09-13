package com.vsii;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEncryptableProperties
@EnableScheduling
public class ClaimE2ioListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaimE2ioListenerApplication.class, args);
	}

}
