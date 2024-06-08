package com.devsu.ClientePersonaService.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue clienteCuentaQueue;

    public void send(String message) {
        rabbitTemplate.convertAndSend(clienteCuentaQueue.getName(), message);
    }
}
