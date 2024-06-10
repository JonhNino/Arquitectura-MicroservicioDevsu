package com.devsu.ClientePersonaService.controller;

import com.devsu.ClientePersonaService.exception.EmptyReportException;
import com.devsu.ClientePersonaService.model.ErrorResponse;
import com.devsu.ClientePersonaService.model.clientFechas.ClientFechas;
import com.devsu.ClientePersonaService.model.reporte.Reporte;
import com.devsu.ClientePersonaService.utils.Constants;
import com.devsu.ClientePersonaService.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.*;

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

    private ConcurrentHashMap<Long, CompletableFuture<String>> futureResponses = new ConcurrentHashMap<>();

    public ReporterController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<ErrorResponse> getReportes(
            @RequestParam("fecha") String fecha,
            @RequestParam("cliente") Long clienteId) {

        CompletableFuture<String> futureResponse = new CompletableFuture<>();
        futureResponses.put(clienteId, futureResponse);

        String[] fechas = fecha.split(":");
        log.info("RequestParam " + fecha + " " + clienteId);

        String message = "";
        if (fechas.length == 1) {
            message = sendClientFechas(clienteId, LocalDate.parse(fechas[0]), LocalDate.parse(fechas[0]));
        } else if (fechas.length == 2) {
            message = sendClientFechas(clienteId, LocalDate.parse(fechas[0]), LocalDate.parse(fechas[1]));
        } else {
            return ResponseEntity.badRequest().body(utils.buildErrorResponse(Constants.BAD_REQUEST, "Fecha Incorrecta"));
        }

        rabbitTemplate.convertAndSend(clienteCuentaQueue.getName(), message);

        try {
            String responseMessage = futureResponse.get(2, TimeUnit.SECONDS); // Obtener el mensaje recibido con un tiempo de espera de 5 segundos
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, responseMessage));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, "Error interno del servidor"));
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof EmptyReportException) {
                return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, "El cliente no tiene cuentas asociadas"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, "Error interno del servidor"));
        } catch (TimeoutException e) {
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, "El Cliente no tiene Movimientos asociadas en estas fechas"+fecha));
        } finally {
            futureResponses.remove(clienteId);
        }
    }


    @RabbitListener(queues = { "${movimientoCliente.queue.name}" })
    public void receiveMovimientoCliente(@Payload String message) {
        log.info("Received message from movimientoCliente {}", message);
        Long clienteId = null;
        try {
            if (message == null || message.trim().isEmpty() || message.equals("{\"cuentas\":[]}")) {
                log.info("Empty message received");
                handleEmptyMessage(message);
                return;
            }

            clienteId = extractClienteIdFromMessage(message); // Método para extraer el clienteId del mensaje

            CompletableFuture<String> futureResponse = futureResponses.get(clienteId);
            if (futureResponse != null) {
                futureResponse.complete(message);
            }
        } catch (EmptyReportException e) {
            log.info("Empty report for client: " + e.getMessage());
            clienteId = extractClienteIdFromMessageFromEmptyReport(message);
            CompletableFuture<String> futureResponse = futureResponses.get(clienteId);
            if (futureResponse != null) {
                futureResponse.complete("{\"type\": \"OK\", \"body\": \"El Cliente no tiene movimientos en estas fechas\"}");
            }
        } catch (Exception e) {
            log.error("Error deserializing JSON from movimientoCliente", e);
            if (clienteId != null) {
                CompletableFuture<String> futureResponse = futureResponses.get(clienteId);
                if (futureResponse != null) {
                    futureResponse.completeExceptionally(e);
                }
            }
        }
    }

    public String sendClientFechas(Long clienteId, LocalDate date1, LocalDate date2){
        ClientFechas clientFechas = new ClientFechas();
        clientFechas.setClientId(clienteId);
        clientFechas.setFecha1(date1);
        clientFechas.setFecha2(date2);
        return utils.convertAndSend(clientFechas);
    }

    private Long extractClienteIdFromMessage(String message) {
        try {
            // Deserializar el mensaje JSON a un objeto MensajeReporte
            Reporte reporte = objectMapper.readValue(message, Reporte.class);
            // Verificar si la lista de cuentas está vacía
            if (reporte.getCuentas().isEmpty()) {
                throw new EmptyReportException("No accounts found for client");
            }
            // Asumimos que todas las cuentas tienen el mismo clienteId
            return reporte.getCuentas().get(0).getClienteId();
        } catch (EmptyReportException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deserializing JSON to extract clienteId", e);
            return null;
        }
    }

    private Long extractClienteIdFromMessageFromEmptyReport(String message) {
        Random random = new Random();
        // Generar un número aleatorio de 10 dígitos
        return 1_000_000_000L + (long) (random.nextDouble() * (9_000_000_000L));
    }

    private void handleEmptyMessage(String message) {
        Long clienteId = extractClienteIdFromMessageFromEmptyReport(message);
        CompletableFuture<String> futureResponse = futureResponses.get(clienteId);
        if (futureResponse != null) {
            futureResponse.complete("{\"type\": \"OK\", \"body\": \"El Cliente no tiene movimientos en estas fechas\"}");
        }
    }
}
