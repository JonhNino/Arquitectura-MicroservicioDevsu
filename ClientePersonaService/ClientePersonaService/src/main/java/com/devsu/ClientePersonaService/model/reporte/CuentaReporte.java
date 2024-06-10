package com.devsu.ClientePersonaService.model.reporte;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CuentaReporte {

    private Long numeroCuenta;
    private String tipoCuenta;

    private BigDecimal saldoInicial;
    private Boolean estado;
    @JsonProperty("clienteId")
    private Long clienteId;
    private List<MovimientoReporte> movimientos;


}
