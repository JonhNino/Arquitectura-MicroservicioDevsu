package com.Devsu.CuentaMovimientosService.controller;
import com.Devsu.CuentaMovimientosService.model.Cuenta;
import com.Devsu.CuentaMovimientosService.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> getAllUsers() {
        return cuentaService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getUserById(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.findUserById(id);
        if (cuenta.isPresent()) {
            return ResponseEntity.ok(cuenta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Cuenta createUser(@RequestBody Cuenta user) {
        return cuentaService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        cuentaService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}


