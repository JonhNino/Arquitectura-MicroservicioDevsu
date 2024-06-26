package com.devsu.ClientePersonaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.Logger;

import static com.devsu.ClientePersonaService.utils.Constants.INICIO_MS;

@SpringBootApplication
@EntityScan(basePackages = "com.devsu.ClientePersonaService.model")
@EnableJpaRepositories(basePackages = "com.devsu.ClientePersonaService.repository")
public class ClientePersonaServiceApplication {
    private static final Logger LOGGER = Logger.getLogger(ClientePersonaServiceApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(ClientePersonaServiceApplication.class, args);
        LOGGER.info(INICIO_MS);
    }

}
