package com.example.common_service.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "persona")
@Data
public class Persona  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String genero;

    private Integer edad;

    @Column(unique = true, nullable = false)
    private String identificacion;

    private String direccion;

    private String telefono;
}
