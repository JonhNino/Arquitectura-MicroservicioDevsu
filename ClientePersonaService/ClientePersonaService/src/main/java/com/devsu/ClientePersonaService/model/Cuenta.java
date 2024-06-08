package com.devsu.ClientePersonaService.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Cuenta")
@Getter
@Setter
@ToString
public class Cuenta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CuentaID")
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroCuenta;

    @Column(nullable = false)
    private String tipoCuenta;

    @Column(nullable = false)
    private double saldoInicial;

    @Column(nullable = false)
    private String estado;
}
