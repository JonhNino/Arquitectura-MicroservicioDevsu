
package com.example.common_service.repository;

public interface UserCuenta {
    Long getId();
    String getNumeroCuenta();
    String getTipoCuenta();
    double getSaldoInicial();
    boolean isEstado();
}
