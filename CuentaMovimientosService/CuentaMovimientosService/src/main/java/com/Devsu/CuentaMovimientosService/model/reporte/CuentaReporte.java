package com.Devsu.CuentaMovimientosService.model.reporte;

import lombok.Data;

import java.util.List;

@Data
public class CuentaReporte {

    private Long numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private List<MovimientoReporte> movimientos;

    @Override
    public String toString() {
        return "CuentaReporte{" +
                "numeroCuenta=" + numeroCuenta +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", saldoInicial=" + String.format("%.2f", saldoInicial) +
                ", estado=" + estado +
                ", movimientos=" + movimientos +
                '}';
    }
}
