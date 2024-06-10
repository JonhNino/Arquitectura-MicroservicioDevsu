package com.Devsu.CuentaMovimientosService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.Logger;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.Devsu.CuentaMovimientosService.repository")
@EntityScan(basePackages = "com.Devsu.CuentaMovimientosService.model")
public class CuentaMovimientosServiceApplication {
    private static final Logger LOGGER = Logger.getLogger(CuentaMovimientosServiceApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(CuentaMovimientosServiceApplication.class, args);
        LOGGER.info("**********El Microservicio CuentaMovimientosServiceApplication se ha iniciado correctamente.**********");
    }

}
