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
    @Column(name = "id")
    private Long id;

    @Column(name = "numeroCuenta", unique = true, nullable = false)
    private String numeroCuenta;

    @Column(name = "tipoCuenta", nullable = false)
    private String tipoCuenta;

    @Column(name = "saldoInicial", nullable = false)
    private double saldoInicial;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
