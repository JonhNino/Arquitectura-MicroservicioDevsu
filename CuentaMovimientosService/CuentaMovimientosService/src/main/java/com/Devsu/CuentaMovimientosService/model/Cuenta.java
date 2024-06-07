package com.Devsu.CuentaMovimientosService.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "cuenta")
@Data
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroCuenta;

    @Column(nullable = false)
    private String tipoCuenta;

    @Column(nullable = false)
    private double saldoInicial;

    @Column(nullable = false)
    private boolean estado;

    // SE necesita validar ya que su comunicacion es asincrona
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

     */
}
