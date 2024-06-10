package com.Devsu.CuentaMovimientosService.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cliente")
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "estado", nullable = false)
    private boolean estado;
}

