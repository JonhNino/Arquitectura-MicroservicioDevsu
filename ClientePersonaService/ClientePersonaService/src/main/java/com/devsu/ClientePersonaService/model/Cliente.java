package com.devsu.ClientePersonaService.model;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "Cliente")
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClienteID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PersonaID", nullable = false)
    private Persona persona;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private boolean estado;
}
