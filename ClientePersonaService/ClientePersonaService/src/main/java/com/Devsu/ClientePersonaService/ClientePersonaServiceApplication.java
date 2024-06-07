package com.Devsu.ClientePersonaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Logger;

@SpringBootApplication
public class ClientePersonaServiceApplication {
	private static final Logger LOGGER = Logger.getLogger(ClientePersonaServiceApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(ClientePersonaServiceApplication.class, args);
		LOGGER.info("El Microservicio ClientePersonaServiceApplication se ha iniciado correctamente.");
	}

}
