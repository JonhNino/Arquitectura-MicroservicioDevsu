package com.Devsu.CuentaMovimientosService.mq;

import com.Devsu.CuentaMovimientosService.model.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Consumer {
    private final ObjectMapper objectMapper;

    public Consumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = { "${clienteCuenta.queue.name}" })
    public void receive(@Payload String message) {
        log.info("Received message {}", message);
        makeSlow();
        try {
            Cliente receivedCliente = objectMapper.readValue(message, Cliente.class);
            log.info("Deserialized Cliente: {}", receivedCliente);
            log.info("Deserialized Cliente: {}", receivedCliente.getPersona().getNombre());
            // Aquí puedes hacer lo que necesites con el objeto deserializado
        } catch (Exception e) {
            log.error("Error deserializing JSON to Cliente", e);
        }
    }

    private void makeSlow() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
