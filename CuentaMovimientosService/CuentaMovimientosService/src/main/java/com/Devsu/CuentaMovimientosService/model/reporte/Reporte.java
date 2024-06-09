package com.Devsu.CuentaMovimientosService.model.reporte;

import com.Devsu.CuentaMovimientosService.model.Cuenta;
import lombok.Data;

import java.util.List;

@Data
public class Reporte {

    private List<CuentaReporte> cuentas;

}
