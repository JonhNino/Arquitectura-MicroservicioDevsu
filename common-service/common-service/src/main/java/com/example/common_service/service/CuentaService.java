package com.example.common_service.service;



import com.example.common_service.model.Cuenta;
import com.example.common_service.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void deleteUser(Long id) {
        cuentaRepository.deleteById(id);
    }
}
