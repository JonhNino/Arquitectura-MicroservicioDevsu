package com.devsu.ClientePersonaService.model.reporte;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovimientoReporte {
    private Long id;
    private LocalDate fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

}
