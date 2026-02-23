package com.bichomania.clinicavet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.bichomania.clinicavet.infrastructure.persistence")

public class BichomaniaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BichomaniaServerApplication.class, args);
    }
}