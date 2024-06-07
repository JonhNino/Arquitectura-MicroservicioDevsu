package com.Devsu.CuentaMovimientosService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Logger;
@SpringBootApplication
public class CuentaMovimientosServiceApplication {
	private static final Logger LOGGER = Logger.getLogger(CuentaMovimientosServiceApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(CuentaMovimientosServiceApplication.class, args);
		LOGGER.info("El Microservicio CuentaMovimientosServiceApplication se ha iniciado correctamente.");
	}

}
