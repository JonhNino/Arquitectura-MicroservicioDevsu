package com.Devsu.CuentaMovimientosService.controller;

import com.Devsu.CuentaMovimientosService.model.Cuenta;
import com.Devsu.CuentaMovimientosService.model.ErrorResponse;
import com.Devsu.CuentaMovimientosService.service.CuentaService;
import com.Devsu.CuentaMovimientosService.utils.Constants;
import com.Devsu.CuentaMovimientosService.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private Utils utils;

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
    public ResponseEntity<ErrorResponse> createUser(@RequestBody Cuenta user) {
        try {
            Cuenta postCuenta = cuentaService.saveUser(user);
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.CUENTA_CREADO + postCuenta));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ErrorResponse> updateUser(@PathVariable Long id, @RequestBody Cuenta user) {
        try {
            Cuenta updatedUser = cuentaService.updateUser(id, user);
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.CUENTA_ACTUALIZADO + updatedUser));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteUser(@PathVariable Long id) {
        try {
            cuentaService.deleteUser(id);
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.CUENTA_ELIMINADO + id));
        } catch (Exception e) {
            return new ResponseEntity<>(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


