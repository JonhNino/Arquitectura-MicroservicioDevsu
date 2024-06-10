package com.Devsu.CuentaMovimientosService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
@ToString
public class Cuenta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numeroCuenta")
    private Long id;

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
