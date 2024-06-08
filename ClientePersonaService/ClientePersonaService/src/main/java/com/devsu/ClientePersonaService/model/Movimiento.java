package com.devsu.ClientePersonaService.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Movimientos")
@Data
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovimientoID")
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(nullable = false)
    private String tipoMovimiento;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private double saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CuentaID", nullable = false)
    private Cuenta cuenta;
}
