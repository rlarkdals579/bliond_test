package com.likelion.bliond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BliondApplication {

    public static void main(String[] args) {
        SpringApplication.run(BliondApplication.class, args);
    }

}
