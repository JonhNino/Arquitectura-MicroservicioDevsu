package com.Devsu.CuentaMovimientosService.mq;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter consumerJackson2MessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setIdClassMapping(Map.of(
                "com.Devsu.CuentaMovimientosService.model.Cliente", com.Devsu.CuentaMovimientosService.model.Cliente.class
             //   "com.Devsu.ClientePersonaService.model.Cliente", com.Devsu.ClientePersonaService.model.Cliente.class
        ));
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);

        // Agrega ambos paquetes a la lista de paquetes de confianza
        typeMapper.setTrustedPackages("com.Devsu.CuentaMovimientosService.model", "com.Devsu.ClientePersonaService.model");

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }


    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                                   MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("clienteCuenta");
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(Consumer consumer) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(consumer, "receive");
        messageListenerAdapter.setMessageConverter(consumerJackson2MessageConverter());
        return messageListenerAdapter;
    }
}
