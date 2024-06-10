package com.Devsu.CuentaMovimientosService.service;

import com.Devsu.CuentaMovimientosService.model.Movimiento;
import com.Devsu.CuentaMovimientosService.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Movimiento updateUser(Long id, Movimiento updatedUser) {
        Optional<Movimiento> existingUserOpt = movimientoRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            Movimiento existingUser = existingUserOpt.get();
            existingUser.setFecha(updatedUser.getFecha());
            existingUser.setTipoMovimiento(updatedUser.getTipoMovimiento());
            existingUser.setValor(updatedUser.getValor());
            existingUser.setSaldo(updatedUser.getSaldo());
            existingUser.setCuenta(updatedUser.getCuenta());
            return movimientoRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    public void deleteUser(Long id) {
        movimientoRepository.deleteById(id);
    }
}


