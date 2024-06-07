package com.Devsu.ClientePersonaService.mq;
import com.Devsu.ClientePersonaService.dummy.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue clienteCuentaQueue;

    public void send(Data message) {
        rabbitTemplate.convertAndSend(clienteCuentaQueue.getName(), message);
    }
}
