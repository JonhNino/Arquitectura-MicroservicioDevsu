package com.Devsu.CuentaMovimientosService.repository;

import com.Devsu.CuentaMovimientosService.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}

