package uk.co.xsc.intercom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

// This exclude disables default error handling
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class IntercomApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntercomApplication.class, args);
    }

}
