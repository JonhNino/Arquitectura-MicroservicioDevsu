package com.Devsu.CuentaMovimientosService.mq;

import com.Devsu.CuentaMovimientosService.model.clientFechas.ClientFechas;
import com.Devsu.CuentaMovimientosService.service.CuentaService;
import com.Devsu.CuentaMovimientosService.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.Devsu.CuentaMovimientosService.utils.Constants.*;

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
    @Value("${clienteCuenta.queue.name}")
    private String clienteCuentaQueueName;

    @Value("${movimientoCliente.queue.name}")
    private String movimientoClienteQueueName;

    public Consumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = CLIENTE_CUENTA_QUEUE_NAME)
    public void receive(String message) {
        log.info("Received message {}", message);
        try {
            ClientFechas receivedCliente = objectMapper.readValue(message, ClientFechas.class);
            if (receivedCliente.getFecha1() == null || receivedCliente.getFecha2() == null) {
                log.error(NULL_DATE_MESSAGE, message);
                return;
            }

            rabbitTemplate.convertAndSend(movimientoClienteQueueName, utils.convertAndSend(cuentaService.getCuentasConMovimientos(receivedCliente.getClientId(), receivedCliente.getFecha1().toString(), receivedCliente.getFecha2().toString(), receivedCliente.getFecha1().toString())));
        } catch (Exception e) {
            log.error(JSON_DESERIALIZE_ERROR_MESSAGE, e);
        }
    }

}
