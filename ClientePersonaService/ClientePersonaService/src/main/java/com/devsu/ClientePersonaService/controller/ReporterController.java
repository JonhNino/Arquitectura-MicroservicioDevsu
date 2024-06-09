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
import java.util.concurrent.ExecutionException;
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
    private CompletableFuture<String> futureResponse = new CompletableFuture<>();
    @GetMapping
    public ResponseEntity<ErrorResponse> getReportes(
            @RequestParam("fecha") String fecha,
            @RequestParam("cliente") Long clienteId) {

        String[] fechas = fecha.split(":");
        log.info("RequestParam" + fecha + clienteId);

        String message = "";
        if (fechas.length == 1) {
            message = sendClientFechas(clienteId, LocalDate.parse(fechas[0]), LocalDate.parse(fechas[0]));
        } else if (fechas.length == 2) {
            message = sendClientFechas(clienteId, LocalDate.parse(fechas[0]), LocalDate.parse(fechas[1]));
        } else {
            return ResponseEntity.badRequest().body(utils.buildErrorResponse(Constants.BAD_REQUEST, "Fecha Incorrecta"));
        }

        rabbitTemplate.convertAndSend(clienteCuentaQueue.getName(), message);

        synchronized (futureResponse) {
            try {
                futureResponse.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, "Error interno del servidor"));
            }
        }

        String responseMessage;
        try {
            responseMessage = futureResponse.get(); // Obtener el mensaje recibido
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, "Error interno del servidor"));
        }

        return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, responseMessage));
    }


    @RabbitListener(queues = { "${movimientoCliente.queue.name}" })
    public void receiveMovimientoCliente(@Payload String message) {
        log.info("Received message from movimientoCliente {}", message);
        try {
            // Procesar el mensaje
            futureResponse.complete(message);
            synchronized (futureResponse) {
                futureResponse.notify();  // Notificar al método getReportes que el mensaje ha sido recibido
            }
        } catch (Exception e) {
            log.error("Error deserializing JSON from movimientoCliente", e);
        }
    }

    public String sendClientFechas(Long clienteId, LocalDate date1, LocalDate date2){

        ClientFechas clientFechas = new ClientFechas();
        clientFechas.setClientId(clienteId);
        clientFechas.setFecha1(date1);
        clientFechas.setFecha2(date2);
        return  utils.convertAndSend(clientFechas);
    }


}


