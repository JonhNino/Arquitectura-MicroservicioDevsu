package com.devsu.ClientePersonaService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "Movimientos")
@Data
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento;

    @Column(name = "valor", nullable = false)
    private double valor;

    @Column(name = "saldo", nullable = false)
    private double saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    @JsonIgnoreProperties({"movimientos", "tipoCuenta", "saldoInicial", "estado", "cliente"})  // Ignora propiedades innecesarias
    private Cuenta cuenta;

    @Override
    public String toString() {
        return "Movimiento{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", tipoMovimiento='" + tipoMovimiento + '\'' +
                ", valor=" + valor +
                ", saldo=" + saldo +
                ", cuentaId=" + (cuenta != null ? cuenta.getId() : null) +
                '}';
    }
}

