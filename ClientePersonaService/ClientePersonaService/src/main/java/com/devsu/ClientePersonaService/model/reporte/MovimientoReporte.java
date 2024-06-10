package com.devsu.ClientePersonaService.model.reporte;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MovimientoReporte {
    private Long id;
    private LocalDate fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;

}
