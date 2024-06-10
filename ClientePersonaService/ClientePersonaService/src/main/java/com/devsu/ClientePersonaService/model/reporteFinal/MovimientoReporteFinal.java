package com.devsu.ClientePersonaService.model.reporteFinal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovimientoReporteFinal {

    @JsonProperty("id_movimiento")
    private Long id;

    @JsonProperty("fecha")
    private LocalDate fecha;

    @JsonProperty("tipo_movimiento")
    private String tipoMovimiento;

    @JsonProperty("valor")
    private Double valor;

    @JsonProperty("saldo")
    private Double saldo;
}