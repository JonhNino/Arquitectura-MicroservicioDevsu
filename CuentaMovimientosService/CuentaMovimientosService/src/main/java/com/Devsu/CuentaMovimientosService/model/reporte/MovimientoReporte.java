package com.Devsu.CuentaMovimientosService.model.reporte;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovimientoReporte {
    private Long id;
    private LocalDate fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

    @Override
    public String toString() {
        return "MovimientoReporte{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", tipoMovimiento='" + tipoMovimiento + '\'' +
                ", valor=" + String.format("%.2f", valor) +
                ", saldo=" + String.format("%.2f", saldo) +
                '}';
    }
}
