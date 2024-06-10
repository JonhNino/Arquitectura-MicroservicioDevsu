package com.devsu.ClientePersonaService.model.reporteFinal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReporteFinal {

    @JsonProperty("cant_cuentas")
    private int cantCuentas;

    @JsonProperty("fecha_reporte")
    private LocalDate fechaReporte;

    @JsonProperty("name")
    private String name;
    @JsonProperty("cuentas")
    private List<CuentaReporteFinal> cuentas;


}
