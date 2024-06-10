package com.Devsu.CuentaMovimientosService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos;

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
        if (movimientos != null) {
            for (Movimiento movimiento : movimientos) {
                movimiento.setCuenta(this);
            }
        }
    }
    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", saldoInicial=" + saldoInicial +
                ", estado=" + estado +
                ", cliente=" + cliente +
                ", movimientos=" + (movimientos != null ? movimientos.size() : null) +
                '}';
    }
}
