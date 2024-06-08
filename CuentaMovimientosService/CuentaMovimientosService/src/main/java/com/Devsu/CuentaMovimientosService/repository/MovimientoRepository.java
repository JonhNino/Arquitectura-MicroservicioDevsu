package com.Devsu.CuentaMovimientosService.repository;

import com.Devsu.CuentaMovimientosService.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}

