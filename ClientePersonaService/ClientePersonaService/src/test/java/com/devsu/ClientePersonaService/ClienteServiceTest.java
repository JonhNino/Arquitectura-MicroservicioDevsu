package com.devsu.ClientePersonaService;

import com.devsu.ClientePersonaService.model.Cliente;
import com.devsu.ClientePersonaService.model.Persona;
import com.devsu.ClientePersonaService.repository.ClienteRepository;
import com.devsu.ClientePersonaService.repository.PersonaRepository;
import com.devsu.ClientePersonaService.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PersonaRepository personaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        // Arrange
        Persona persona = new Persona();
        persona.setId(1L);
        persona.setNombre("John Doe");

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setPersona(persona);
        cliente.setContrasena("password");
        cliente.setEstado(true);

        when(personaRepository.save(any(Persona.class))).thenReturn(persona);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente savedCliente = clienteService.saveUser(cliente);

        // Assert
        assertEquals(cliente, savedCliente);
        verify(personaRepository, times(1)).save(any(Persona.class));
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testFindUserById() {
        // Arrange
        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        Optional<Cliente> foundCliente = clienteService.findUserById(clienteId);

        // Assert
        assertEquals(cliente, foundCliente.get());
        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        Long clienteId = 1L;
        Persona persona = new Persona();
        persona.setId(1L);
        persona.setNombre("New Name");

        Cliente existingCliente = new Cliente();
        existingCliente.setId(clienteId);
        existingCliente.setPersona(persona);
        existingCliente.setContrasena("oldPassword");
        existingCliente.setEstado(true);

        Cliente updatedCliente = new Cliente();
        updatedCliente.setPersona(persona);
        updatedCliente.setContrasena("newPassword");
        updatedCliente.setEstado(false);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(existingCliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(existingCliente);
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);

        // Act
        Cliente result = clienteService.updateUser(clienteId, updatedCliente);

        // Assert
        assertEquals("newPassword", result.getContrasena());
        assertEquals(false, result.isEstado());
        assertEquals("New Name", result.getPersona().getNombre());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(personaRepository, times(1)).save(any(Persona.class));
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long clienteId = 1L;

        // Act
        clienteService.deleteUser(clienteId);

        // Assert
        verify(clienteRepository, times(1)).deleteById(clienteId);
    }

    @Test
    void testUpdateUser_NotFound() {
        // Arrange
        Long clienteId = 1L;
        Cliente updatedCliente = new Cliente();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.updateUser(clienteId, updatedCliente);
        });

        assertEquals("User not found with id: " + clienteId, exception.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
    }
}
