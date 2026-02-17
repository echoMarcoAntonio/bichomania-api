package com.bichomania.clinicavet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BichomaniaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BichomaniaServerApplication.class, args);
    }
}