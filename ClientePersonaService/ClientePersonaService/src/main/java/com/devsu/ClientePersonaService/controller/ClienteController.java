package com.devsu.ClientePersonaService.controller;

import com.devsu.ClientePersonaService.model.Cliente;
import com.devsu.ClientePersonaService.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

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
    public Cliente createUser(@RequestBody Cliente user) {
        return clienteService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        clienteService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}


