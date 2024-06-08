package com.Devsu.CuentaMovimientosService.service;

import com.Devsu.CuentaMovimientosService.model.Movimiento;
import com.Devsu.CuentaMovimientosService.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    public List<Movimiento> findAllUsers() {
        return movimientoRepository.findAll();
    }

    public Optional<Movimiento> findUserById(Long id) {
        return movimientoRepository.findById(id);
    }

    public Movimiento saveUser(Movimiento user) {
        return movimientoRepository.save(user);
    }

    public void deleteUser(Long id) {
        movimientoRepository.deleteById(id);
    }
}


