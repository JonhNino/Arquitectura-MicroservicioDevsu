package com.devsu.ClientePersonaService.utils;

import com.devsu.ClientePersonaService.model.reporte.CuentaReporte;
import com.devsu.ClientePersonaService.model.reporte.MovimientoReporte;
import com.devsu.ClientePersonaService.model.reporte.Reporte;
import com.devsu.ClientePersonaService.model.reporteFinal.CuentaReporteFinal;
import com.devsu.ClientePersonaService.model.reporteFinal.MovimientoReporteFinal;
import com.devsu.ClientePersonaService.model.reporteFinal.ReporteFinal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReportConversionUtils {
    public static ReporteFinal convertToReporteFinal(Reporte reporte, int cantCuentas, LocalDate fechaReporte, String name) {
        ReporteFinal reporteFinal = new ReporteFinal();
        reporteFinal.setCuentas(reporte.getCuentas().stream()
                .map(ReportConversionUtils::convertToCuentaReporteFinal)
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
                .map(ReportConversionUtils::convertToMovimientoReporteFinal)
                .collect(Collectors.toList()));
        cuentaReporteFinal.setSaldoDisponible(cuentaReporteFinal.getSaldoInicial().add(calculateSaldoDisponible(cuentaReporteFinal.getMovimientos())));
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

    private static BigDecimal calculateSaldoDisponible(List<MovimientoReporteFinal> movimientos) {
        BigDecimal totalDepositos = movimientos.stream()
                .filter(mov -> "Depósito".equalsIgnoreCase(mov.getTipoMovimiento()))
                .map(MovimientoReporteFinal::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalRetiros = movimientos.stream()
                .filter(mov -> "Retiro".equalsIgnoreCase(mov.getTipoMovimiento()))
                .map(MovimientoReporteFinal::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalDepositos.subtract(totalRetiros);
    }
}
