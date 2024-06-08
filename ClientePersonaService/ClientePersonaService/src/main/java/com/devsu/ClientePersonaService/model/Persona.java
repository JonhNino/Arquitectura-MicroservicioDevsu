package com.devsu.ClientePersonaService.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "Persona")
@Data
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PersonaID")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 1)
    private String genero;

    @Column(nullable = false)
    private Integer edad;

    @Column(unique = true, nullable = false)
    private String identificacion;

    private String direccion;

    private String telefono;
}
