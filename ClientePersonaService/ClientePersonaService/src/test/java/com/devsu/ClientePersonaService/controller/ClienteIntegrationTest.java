package com.devsu.ClientePersonaService.controller;


import com.devsu.ClientePersonaService.model.Cliente;
import com.devsu.ClientePersonaService.model.Persona;
import com.devsu.ClientePersonaService.repository.ClienteRepository;
import com.devsu.ClientePersonaService.repository.PersonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @BeforeEach
    void setup() {
        clienteRepository.deleteAll();
        personaRepository.deleteAll();
    }

    @Test
    void shouldCreateClient() throws Exception {
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"persona\": { \"nombre\": \"Juan\", \"genero\": \"M\", \"edad\": 30, \"identificacion\": \"123456789\", \"direccion\": \"Calle 123\", \"telefono\": \"555-5555\" }, \"contrasena\": \"password123\", \"estado\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("OK"))
                .andExpect(jsonPath("$.body").value("CLIENTE Creado: Cliente(id=1, persona=Persona(id=1, nombre=Juan, genero=M, edad=30, identificacion=123456789, direccion=Calle 123, telefono=555-5555), contrasena=password123, estado=true)"));
    }

    @Test
    void shouldGetAllClients() throws Exception {
        // Add some clients to the repository for testing
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setGenero("M");
        persona.setEdad(30);
        persona.setIdentificacion("123456789");
        persona.setDireccion("Calle 123");
        persona.setTelefono("555-5555");
        personaRepository.save(persona);

        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        cliente.setContrasena("password123");
        cliente.setEstado(true);
        clienteRepository.save(cliente);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].persona.nombre").value("Juan"));
    }
}
