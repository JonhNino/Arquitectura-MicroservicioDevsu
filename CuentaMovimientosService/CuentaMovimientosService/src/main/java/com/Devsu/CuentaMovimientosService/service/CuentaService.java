package com.Devsu.CuentaMovimientosService.service;

import com.Devsu.CuentaMovimientosService.model.Cuenta;
import com.Devsu.CuentaMovimientosService.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> findAllUsers() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> findUserById(Long id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta saveUser(Cuenta user) {
        return cuentaRepository.save(user);
    }

    @Transactional
    public Cuenta updateUser(Long id, Cuenta updatedUser) {
        Optional<Cuenta> existingUserOpt = cuentaRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            Cuenta existingUser = existingUserOpt.get();
            existingUser.setTipoCuenta(updatedUser.getTipoCuenta());
            existingUser.setSaldoInicial(updatedUser.getSaldoInicial());
            existingUser.setEstado(updatedUser.isEstado());
            existingUser.setCliente(updatedUser.getCliente());
            return cuentaRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }
    public void deleteUser(Long id) {
        cuentaRepository.deleteById(id);
    }
}


