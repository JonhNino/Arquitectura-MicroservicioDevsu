package com.devsu.ClientePersonaService.repository;

import java.util.Date;

public interface UserMovimiento {
    Long getId();
    Date getFecha();
    String getTipoMovimiento();
    double getValor();
    double getSaldo();
}
