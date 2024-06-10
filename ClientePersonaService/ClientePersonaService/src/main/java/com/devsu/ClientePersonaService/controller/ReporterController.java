package com.devsu.ClientePersonaService.controller;

import com.devsu.ClientePersonaService.exception.EmptyReportException;
import com.devsu.ClientePersonaService.model.ErrorResponse;
import com.devsu.ClientePersonaService.service.ReportService;
import com.devsu.ClientePersonaService.utils.Constants;
import com.devsu.ClientePersonaService.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.*;

@RestController
@RequestMapping("/reportes")
@Slf4j
public class ReporterController {

    @Autowired
    private Utils utils;
    @Autowired
    private ReportService reportService;

    private ConcurrentHashMap<Long, CompletableFuture<String>> futureResponses = new ConcurrentHashMap<>();

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
            message = utils.sendClientFechas(clienteId, LocalDate.parse(fechas[0]), LocalDate.parse(fechas[0]));
        } else if (fechas.length == 2) {
            message = utils.sendClientFechas(clienteId, LocalDate.parse(fechas[0]), LocalDate.parse(fechas[1]));
        } else {
            return ResponseEntity.badRequest().body(utils.buildErrorResponse(Constants.BAD_REQUEST, Constants.FECHA_INCORRECTA, null));
        }

        reportService.sendReportRequest(message, clienteId, futureResponse);

        try {
            String responseMessage = futureResponse.get(2, TimeUnit.SECONDS);
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.REPORTE_CREADO, reportService.processResponse(responseMessage)));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR, null));
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof EmptyReportException) {
                return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.CLIENTE_NO_CUENTAS, null));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR, null));
        } catch (TimeoutException e) {
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.CLIENTE_NO_MOVIMIENTOS + fecha, null));
        } finally {
            futureResponses.remove(clienteId);
        }
    }
}
