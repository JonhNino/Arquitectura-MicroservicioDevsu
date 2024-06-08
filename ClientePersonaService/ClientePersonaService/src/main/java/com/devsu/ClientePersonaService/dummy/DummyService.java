package com.devsu.ClientePersonaService.dummy;

import com.devsu.ClientePersonaService.ClientePersonaServiceApplication;
import com.devsu.ClientePersonaService.model.Cliente;
import com.devsu.ClientePersonaService.model.Cuenta;
import com.devsu.ClientePersonaService.model.Movimiento;
import com.devsu.ClientePersonaService.model.Persona;
import com.devsu.ClientePersonaService.mq.Publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Slf4j
@Service
public class DummyService {
    private static final Logger LOGGER = Logger.getLogger(DummyService.class.getName());

    @Autowired
    private Publisher publisher;

    private final ObjectMapper objectMapper;

    public DummyService(Publisher publisher) {
        this.publisher = publisher;
        this.objectMapper = new ObjectMapper();
    }


    public void sendToRabbit(String message) {
        Persona persona = new Persona();
        persona.setId(12L);
        persona.setNombre("John");

        Cliente cliente = new Cliente();
        cliente.setEstado(true);
        cliente.setPersona(persona);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("2324da32");
        cuenta.setCliente(cliente);

        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento("Dinero");
        movimiento.setCuenta(cuenta);

        try {
            String clienteJson = objectMapper.writeValueAsString(cliente);
            log.info("Cliente JSON: {}", clienteJson);
            this.publisher.send(clienteJson);
        } catch (Exception e) {
            log.error("Error converting Cliente to JSON", e);
        }
    }

}