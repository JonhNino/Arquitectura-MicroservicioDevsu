package com.devsu.ClientePersonaService.mq;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfig {

    @Bean
    public Queue publisherQueue() {
        return new Queue("clienteCuenta", true);
    }
}
