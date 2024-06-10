package com.devsu.ClientePersonaService.model.reporteFinal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CuentaReporteFinal {

    @JsonProperty("numero_cuenta")
    private Long numeroCuenta;

    @JsonProperty("tipo_cuenta")
    private String tipoCuenta;

    @JsonProperty("saldo_inicial")
    private Double saldoInicial;

    @JsonProperty("estado")
    private Boolean estado;

    @JsonProperty("saldo_disponible")
    private Double saldoDisponible;

    @JsonProperty("movimientos")
    private List<MovimientoReporteFinal> movimientos;
}

