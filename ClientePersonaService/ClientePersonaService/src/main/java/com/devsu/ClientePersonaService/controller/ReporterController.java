package com.devsu.ClientePersonaService.controller;

import com.devsu.ClientePersonaService.model.ErrorResponse;
import com.devsu.ClientePersonaService.model.clientFechas.ClientFechas;
import com.devsu.ClientePersonaService.utils.Constants;
import com.devsu.ClientePersonaService.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/reportes")
@Slf4j
public class ReporterController {

    private final ObjectMapper objectMapper;

    @Autowired
    private Utils utils;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue clienteCuentaQueue;

    public ReporterController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<ErrorResponse> getReportes(
            @RequestParam("fecha") String fecha,
            @RequestParam("cliente") Long clienteId) {

        String[] fechas = fecha.split(":");
        log.info("RequestParam"+fecha+clienteId);
        CompletableFuture<String> futureResponse = new CompletableFuture<>();

        if (fechas.length == 1) {

            rabbitTemplate.convertAndSend(clienteCuentaQueue.getName(), sendClientFechas(clienteId,LocalDate.parse(fechas[0]),LocalDate.parse(fechas[0])));

        } else if (fechas.length == 2) {

            rabbitTemplate.convertAndSend(clienteCuentaQueue.getName(), sendClientFechas(clienteId,LocalDate.parse(fechas[0]),LocalDate.parse(fechas[1])));

        } else {
            return ResponseEntity.badRequest().body(utils.buildErrorResponse(Constants.BAD_REQUEST, "Fecha Incorrecta"));
        }
        // aca quiero recibir el mensaje
        return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, "Bien Bien"));
    }


    @RabbitListener(queues = { "${movimientoCliente.queue.name}" })
    public String receive(@Payload String message) {
        log.info("Received message {}", message);
        return  message;
       // try {

       //     ClientFechas receivedCliente = objectMapper.readValue(message, ClientFechas.class);
        //    rabbitTemplate.convertAndSend(clienteCuentaQueue.getName(), utils.convertAndSend(cuentaService.getCuentasConMovimientos(receivedCliente.getClientId(),receivedCliente.getFecha1().toString(),receivedCliente.getFecha2().toString(),receivedCliente.getFecha1().toString())));
        //} catch (Exception e) {
         //   log.error("Error deserializing JSON to Cliente", e);
    //    }
    }

    public String sendClientFechas(Long clienteId, LocalDate date1, LocalDate date2){

        ClientFechas clientFechas = new ClientFechas();
        clientFechas.setClientId(clienteId);
        clientFechas.setFecha1(date1);
        clientFechas.setFecha2(date2);
        return  utils.convertAndSend(clientFechas);
    }


}


