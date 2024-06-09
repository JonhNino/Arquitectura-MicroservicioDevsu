package com.Devsu.CuentaMovimientosService.mq;

import com.Devsu.CuentaMovimientosService.model.Cliente;
import com.Devsu.CuentaMovimientosService.model.clientFechas.ClientFechas;
import com.Devsu.CuentaMovimientosService.service.CuentaService;
import com.Devsu.CuentaMovimientosService.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Component
public class Consumer {
    private final ObjectMapper objectMapper;
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private Utils utils;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue movimientoClienteQueue;

    public Consumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = { "${clienteCuenta.queue.name}" })
    public void receive(@Payload String message) {
        log.info("Received message {}", message);
        try {
            ClientFechas receivedCliente = objectMapper.readValue(message, ClientFechas.class);
            if (receivedCliente.getFecha1() == null || receivedCliente.getFecha2() == null) {
                log.error("Received message with null dates: {}", message);
                return;
            }

            rabbitTemplate.convertAndSend(movimientoClienteQueue.getName(), utils.convertAndSend(cuentaService.getCuentasConMovimientos(receivedCliente.getClientId(),receivedCliente.getFecha1().toString(),receivedCliente.getFecha2().toString(),receivedCliente.getFecha1().toString())));
        } catch (Exception e) {
            log.error("Error deserializing JSON to Cliente", e);
        }
    }

}
