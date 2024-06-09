package com.devsu.ClientePersonaService.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue clienteCuentaQueue() {
        return new Queue("clienteCuenta", true);
    }
    @Bean
    public Queue replyQueue() {
        return new Queue("movimientoCliente", false);
    }
}

