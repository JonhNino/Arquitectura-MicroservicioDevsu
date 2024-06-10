package com.devsu.ClientePersonaService.service;

import com.devsu.ClientePersonaService.exception.EmptyReportException;
import com.devsu.ClientePersonaService.model.Cliente;
import com.devsu.ClientePersonaService.model.reporte.Reporte;
import com.devsu.ClientePersonaService.model.reporteFinal.ReporteFinal;
import com.devsu.ClientePersonaService.utils.ReportConversionUtils;
import com.devsu.ClientePersonaService.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import com.devsu.ClientePersonaService.utils.Constants;

@Service
@Slf4j
public class ReportService {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private Utils utils;
    private final ObjectMapper objectMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue clienteCuentaQueue;

    private ConcurrentHashMap<Long, CompletableFuture<String>> futureResponses = new ConcurrentHashMap<>();

    public ReportService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ReporteFinal processResponse(String responseMessage) {
        try {
            Reporte reporte = utils.parseJsonToReporte(responseMessage);
            int cantCuentas = reporte.getCuentas().size();
            LocalDate fechaReporte = LocalDate.now();

            Optional<Cliente> cuenta = clienteService.findUserById(reporte.getCuentas().get(0).getClienteId());
            String name = cuenta.map(c -> c.getPersona().getNombre()).orElse(Constants.CLIENTE_NO_NOMBRE);

            return ReportConversionUtils.convertToReporteFinal(reporte, cantCuentas, fechaReporte, name);
        } catch (JsonProcessingException e) {
            log.error(Constants.ERROR_PROCESSING_JSON, e);
            return null;
        }
    }

    public void sendReportRequest(String message, Long clienteId, CompletableFuture<String> futureResponse) {
        rabbitTemplate.convertAndSend(clienteCuentaQueue.getName(), message);
        futureResponses.put(clienteId, futureResponse);
    }

    @RabbitListener(queues = {"${movimientoCliente.queue.name}"})
    public void receiveMovimientoCliente(@Payload String message) {
        log.info(Constants.RECEIVEND_MESSAGE, message);
        Long clienteId = null;
        try {
            if (message == null || message.trim().isEmpty() || message.equals("{\"cuentas\":[]}")) {
                log.info(Constants.EMPTY_MESSAGE);
                handleEmptyMessage(message);
                return;
            }

            clienteId = extractClienteIdFromMessage(message);

            CompletableFuture<String> futureResponse = futureResponses.get(clienteId);
            if (futureResponse != null) {
                futureResponse.complete(message);
            }
        } catch (Exception e) {
            log.error(Constants.ERROR_DESERIALIZING_MOVIMIENTCLIENTE, e);
            if (clienteId != null) {
                CompletableFuture<String> futureResponse = futureResponses.get(clienteId);
                if (futureResponse != null) {
                    futureResponse.completeExceptionally(e);
                }
            }
        }
    }

    private Long extractClienteIdFromMessage(String message) {
        try {
            Reporte reporte = objectMapper.readValue(message, Reporte.class);
            if (reporte.getCuentas().isEmpty()) {
                throw new EmptyReportException(Constants.NO_ACCOUNTS_FOR_CLIENT);
            }
            return reporte.getCuentas().get(0).getClienteId();
        } catch (EmptyReportException e) {
            throw e;
        } catch (Exception e) {
            log.error(Constants.ERROR_DESERIALIZING_CLIENTID, e);
            return null;
        }
    }

    private Long extractClienteIdFromMessageFromEmptyReport(String message) {
        Random random = new Random();
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
