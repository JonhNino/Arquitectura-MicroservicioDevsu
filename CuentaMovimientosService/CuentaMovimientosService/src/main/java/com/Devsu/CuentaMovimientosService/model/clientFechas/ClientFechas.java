package com.Devsu.CuentaMovimientosService.model.clientFechas;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientFechas {

    private Long clientId;
    private LocalDate fecha1;
    private LocalDate fecha2;
}
