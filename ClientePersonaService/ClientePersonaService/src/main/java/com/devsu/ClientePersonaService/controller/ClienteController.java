package com.devsu.ClientePersonaService.controller;

import com.devsu.ClientePersonaService.model.Cliente;
import com.devsu.ClientePersonaService.model.ErrorResponse;
import com.devsu.ClientePersonaService.service.ClienteService;
import com.devsu.ClientePersonaService.utils.Constants;
import com.devsu.ClientePersonaService.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private Utils utils;

    @GetMapping
    public List<Cliente> getAllUsers() {

        return clienteService.findAllUsers();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getUserById(@PathVariable Long id) {
        Optional<Cliente> cuenta = clienteService.findUserById(id);
        if (cuenta.isPresent()) {
            return ResponseEntity.ok(cuenta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ErrorResponse> createUser(@RequestBody Cliente user) {
        try {
            Cliente postClient = clienteService.saveUser(user);
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.CLIENTE_CREADO + postClient, null));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, e.toString(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ErrorResponse> updateUser(@PathVariable Long id, @RequestBody Cliente user) {

        try {
            Cliente updatedUser = clienteService.updateUser(id, user);
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.CLIENTE_ACTUALIZADO + updatedUser, null));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, e.toString(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteUser(@PathVariable Long id) {

        try {
            clienteService.deleteUser(id);
            return ResponseEntity.ok(utils.buildErrorResponse(Constants.OK, Constants.CLIENTE_ELIMINADO + id, null));
        } catch (Exception e) {
            return new ResponseEntity<>(utils.buildErrorResponse(Constants.INTERNAL_SERVER_ERROR, e.toString(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}


