package com.example.auth_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class AuthSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthSpringApplication.class, args);
    }

}
