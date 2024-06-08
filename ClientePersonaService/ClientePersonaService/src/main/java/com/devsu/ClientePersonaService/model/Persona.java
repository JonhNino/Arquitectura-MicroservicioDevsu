package com.devsu.ClientePersonaService.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "Persona")
@Data
public class Persona {@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "genero", nullable = false, length = 50)
    private String genero;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "identificacion", unique = true, nullable = false)
    private String identificacion;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;
}
