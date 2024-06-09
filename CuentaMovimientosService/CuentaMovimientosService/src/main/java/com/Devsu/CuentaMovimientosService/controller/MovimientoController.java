package com.Devsu.CuentaMovimientosService.controller;

import com.Devsu.CuentaMovimientosService.model.ErrorResponse;
import com.Devsu.CuentaMovimientosService.model.Movimiento;
import com.Devsu.CuentaMovimientosService.service.MovimientoService;
import com.Devsu.CuentaMovimientosService.utils.Constants;
import com.Devsu.CuentaMovimientosService.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;
    @Autowired
    private Utils utils;

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
    public ResponseEntity<ErrorResponse> createUser(@RequestBody Movimiento user) {
        try {
            Movimiento postMoviento= movimientoService.saveUser(user);
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK,Constants.MOVIMIENTO_CREADO+postMoviento));

        } catch (RuntimeException e) {
            return new ResponseEntity<>(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ErrorResponse> updateUser(@PathVariable Long id, @RequestBody Movimiento user) {
        try {
            Movimiento updatedUser = movimientoService.updateUser(id, user);
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.MOVIMIENTO_ACTUALIZADO + updatedUser));

        } catch (RuntimeException e) {
            return new ResponseEntity<>(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteUser(@PathVariable Long id) {
        try {
            movimientoService.deleteUser(id);

            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.MOVIMIENTO_ELIMINADO + id));

        } catch (Exception e) {
            return new ResponseEntity<>(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

