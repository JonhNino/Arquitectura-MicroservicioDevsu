package com.devsu.ClientePersonaService.utils;

import com.devsu.ClientePersonaService.model.ErrorResponse;
import com.devsu.ClientePersonaService.model.reporte.CuentaReporte;
import com.devsu.ClientePersonaService.model.reporte.MovimientoReporte;
import com.devsu.ClientePersonaService.model.reporte.Reporte;
import com.devsu.ClientePersonaService.model.reporteFinal.CuentaReporteFinal;
import com.devsu.ClientePersonaService.model.reporteFinal.MovimientoReporteFinal;
import com.devsu.ClientePersonaService.model.reporteFinal.ReporteFinal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UtilReport {

    public static Reporte parseJsonToReporte(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(json, Reporte.class);
    }

    public static ReporteFinal processResponse(String responseMessage) {
        try {

            Reporte reporte = parseJsonToReporte(responseMessage);

            int cantCuentas = reporte.getCuentas().size(); 
            LocalDate fechaReporte = LocalDate.now();
            String name = "Reporte de Cuentas";

            ReporteFinal reporteFinal = convertToReporteFinal(reporte, cantCuentas, fechaReporte, name);

            return reporteFinal;
        } catch (JsonProcessingException e) {

            return null;
        }
    }

    public static ErrorResponse buildErrorResponse(String errorCode, String errorDescription) {
        ErrorResponse responseDTO = new ErrorResponse();
        responseDTO.setType(errorCode);
        responseDTO.setBody(errorDescription);
        return responseDTO;
    }

    public static ReporteFinal convertToReporteFinal(Reporte reporte, int cantCuentas, LocalDate fechaReporte, String name) {
        ReporteFinal reporteFinal = new ReporteFinal();

        reporteFinal.setCuentas(reporte.getCuentas().stream()
                .map(UtilReport::convertToCuentaReporteFinal)
                .collect(Collectors.toList()));
        reporteFinal.setCantCuentas(cantCuentas);
        reporteFinal.setFechaReporte(fechaReporte);
        reporteFinal.setName(name);

        return reporteFinal;
    }

    private static CuentaReporteFinal convertToCuentaReporteFinal(CuentaReporte cuentaReporte) {
        CuentaReporteFinal cuentaReporteFinal = new CuentaReporteFinal();

        cuentaReporteFinal.setNumeroCuenta(cuentaReporte.getNumeroCuenta());
        cuentaReporteFinal.setTipoCuenta(cuentaReporte.getTipoCuenta());
        cuentaReporteFinal.setSaldoInicial(cuentaReporte.getSaldoInicial());
        cuentaReporteFinal.setEstado(cuentaReporte.getEstado());

        cuentaReporteFinal.setMovimientos(cuentaReporte.getMovimientos().stream()
                .map(UtilReport::convertToMovimientoReporteFinal)
                .collect(Collectors.toList()));
        cuentaReporteFinal.setSaldoDisponible(cuentaReporte.getSaldoInicial()-calculateSaldoDisponible(cuentaReporteFinal.getMovimientos()));


        return cuentaReporteFinal;
    }

    private static MovimientoReporteFinal convertToMovimientoReporteFinal(MovimientoReporte movimientoReporte) {

        MovimientoReporteFinal movimientoReporteFinal = new MovimientoReporteFinal();

        movimientoReporteFinal.setId(movimientoReporte.getId());
        movimientoReporteFinal.setFecha(movimientoReporte.getFecha());
        movimientoReporteFinal.setTipoMovimiento(movimientoReporte.getTipoMovimiento());
        movimientoReporteFinal.setValor(movimientoReporte.getValor());
        movimientoReporteFinal.setSaldo(movimientoReporte.getSaldo());

        return movimientoReporteFinal;
    }

    private static Double calculateSaldoDisponible(List<MovimientoReporteFinal> movimientos) {
        double totalDepositos = movimientos.stream()
                .filter(mov -> "Depósito".equalsIgnoreCase(mov.getTipoMovimiento()))
                .mapToDouble(MovimientoReporteFinal::getValor)
                .sum();

        double totalRetiros = movimientos.stream()
                .filter(mov -> "Retiro".equalsIgnoreCase(mov.getTipoMovimiento()))
                .mapToDouble(MovimientoReporteFinal::getValor)
                .sum();

        return totalDepositos - totalRetiros;
    }
}
