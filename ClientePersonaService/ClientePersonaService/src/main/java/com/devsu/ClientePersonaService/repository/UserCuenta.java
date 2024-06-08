
package com.devsu.ClientePersonaService.repository;

public interface UserCuenta {
    Long getId();
    String getNumeroCuenta();
    String getTipoCuenta();
    double getSaldoInicial();
    boolean isEstado();
}
