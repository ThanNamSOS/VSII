package vn.systems.claim.services.capture;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "vn.systems.claim.services.capture"
})
@EnableScheduling
@EnableEncryptableProperties
public class ClaimCaptureServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaimCaptureServicesApplication.class, args);
    }

}
