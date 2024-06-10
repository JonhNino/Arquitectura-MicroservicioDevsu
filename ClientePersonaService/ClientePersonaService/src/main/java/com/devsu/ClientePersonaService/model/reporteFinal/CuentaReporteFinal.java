package com.devsu.ClientePersonaService.model.reporteFinal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CuentaReporteFinal {

    @JsonProperty("numero_cuenta")
    private Long numeroCuenta;

    @JsonProperty("tipo_cuenta")
    private String tipoCuenta;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("saldo_inicial")
    private BigDecimal saldoInicial;

    @JsonProperty("estado")
    private Boolean estado;

    @JsonProperty("saldo_disponible")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal saldoDisponible;

    @JsonProperty("movimientos")
    private List<MovimientoReporteFinal> movimientos;
}

