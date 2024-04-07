package edu.monash.bridgingculture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BridgingCultureApplication {

    public static void main(String[] args) {
        SpringApplication.run(BridgingCultureApplication.class, args);
    }

}
