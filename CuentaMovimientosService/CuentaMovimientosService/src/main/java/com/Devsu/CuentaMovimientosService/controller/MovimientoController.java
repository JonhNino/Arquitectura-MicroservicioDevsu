package com.Devsu.CuentaMovimientosService.controller;
import com.Devsu.CuentaMovimientosService.model.Movimiento;
import com.Devsu.CuentaMovimientosService.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> getAllUsers() {
        return movimientoService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> getUserById(@PathVariable Long id) {
        Optional<Movimiento> movimiento = movimientoService.findUserById(id);
        if (movimiento.isPresent()) {
            return ResponseEntity.ok(movimiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Movimiento createUser(@RequestBody Movimiento user) {
        return movimientoService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        movimientoService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

