package com.devsu.ClientePersonaService.service;


import com.devsu.ClientePersonaService.model.Cliente;
import com.devsu.ClientePersonaService.model.Persona;
import com.devsu.ClientePersonaService.repository.ClienteRepository;
import com.devsu.ClientePersonaService.repository.PersonaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    public List<Cliente> findAllUsers() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findUserById(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente saveUser(Cliente user) {
        // Guarda la entidad Persona primero
        Persona persona = user.getPersona();
        Persona savedPersona = personaRepository.save(persona);

        // Asigna la entidad Persona guardada a la entidad Cliente
        user.setPersona(savedPersona);

        // Guarda la entidad Cliente
        return clienteRepository.save(user);
    }

    @Transactional
    public Cliente updateUser(Long id, Cliente updatedUser) {
        Optional<Cliente> existingUserOpt = clienteRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            Cliente existingUser = existingUserOpt.get();

            // Update Persona entity
            Persona existingPersona = existingUser.getPersona();
            Persona updatedPersona = updatedUser.getPersona();
            existingPersona.setNombre(updatedPersona.getNombre());
            existingPersona.setGenero(updatedPersona.getGenero());
            existingPersona.setEdad(updatedPersona.getEdad());
            existingPersona.setIdentificacion(updatedPersona.getIdentificacion());
            existingPersona.setDireccion(updatedPersona.getDireccion());
            existingPersona.setTelefono(updatedPersona.getTelefono());
            personaRepository.save(existingPersona);

            // Update Cliente entity
            existingUser.setContrasena(updatedUser.getContrasena());
            existingUser.setEstado(updatedUser.isEstado());
            return clienteRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }


    public void deleteUser(Long id) {
        clienteRepository.deleteById(id);
    }
}


