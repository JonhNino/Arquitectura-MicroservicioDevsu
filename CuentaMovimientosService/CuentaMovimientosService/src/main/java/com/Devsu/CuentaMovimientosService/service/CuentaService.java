package com.Devsu.CuentaMovimientosService.service;

import com.Devsu.CuentaMovimientosService.model.Cuenta;
import com.Devsu.CuentaMovimientosService.utils.Constants;
import com.Devsu.CuentaMovimientosService.model.reporte.CuentaReporte;
import com.Devsu.CuentaMovimientosService.model.reporte.MovimientoReporte;
import com.Devsu.CuentaMovimientosService.model.reporte.Reporte;
import com.Devsu.CuentaMovimientosService.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;

@Service
public class CuentaService {

    private static final Logger LOGGER = Logger.getLogger(CuentaService.class.getName());


    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Cuenta> findAllUsers() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> findUserById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Transactional
    public Cuenta saveUser(Cuenta user) {
        // Asociar movimientos con la cuenta antes de guardar
        if (user.getMovimientos() != null) {
            user.getMovimientos().forEach(movimiento -> movimiento.setCuenta(user));
        }
        return cuentaRepository.save(user);
    }

    @Transactional
    public Cuenta updateUser(Long id, Cuenta updatedUser) {
        Optional<Cuenta> existingUserOpt = cuentaRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            Cuenta existingUser = existingUserOpt.get();
            existingUser.setTipoCuenta(updatedUser.getTipoCuenta());
            existingUser.setSaldoInicial(updatedUser.getSaldoInicial());
            existingUser.setEstado(updatedUser.isEstado());
            existingUser.setCliente(updatedUser.getCliente());
            return cuentaRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    public void deleteUser(Long id) {
        cuentaRepository.deleteById(id);
    }

    public Reporte getCuentasConMovimientos(Long clienteId, String fechaInicio, String fechaFin, String fechaUnica) {
        String sql = "SELECT c.numero_cuenta, c.tipo_cuenta, c.saldo_inicial, c.estado, " +
                "m.id AS movimiento_id, m.fecha, m.tipo_movimiento, m.valor, m.saldo " +
                "FROM cuenta c " +
                "LEFT JOIN movimientos m ON c.numero_cuenta = m.cuenta_id " +
                "WHERE c.cliente_id = ? AND ((m.fecha BETWEEN ? AND ?) OR (m.fecha = ?))";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, clienteId, fechaInicio, fechaFin, fechaUnica);
        Map<Long, CuentaReporte> cuentaMap = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long numeroCuenta = (Long) row.get(Constants.NUMERO_CUENTA);

            if (!cuentaMap.containsKey(numeroCuenta)) {
                CuentaReporte cuentaReporte = new CuentaReporte();
                cuentaReporte.setNumeroCuenta(numeroCuenta);
                cuentaReporte.setTipoCuenta((String) row.get(Constants.TIPO_CUENTA));
                cuentaReporte.setSaldoInicial((Double) row.get(Constants.SALDO_INICIAL));
                cuentaReporte.setEstado((Boolean) row.get(Constants.ESTADO));
                cuentaReporte.setClienteId(clienteId);
                cuentaReporte.setMovimientos(new ArrayList<>());
                cuentaMap.put(numeroCuenta, cuentaReporte);
            }

            MovimientoReporte movimientoReporte = new MovimientoReporte();
            movimientoReporte.setId((Long) row.get(Constants.MOVIMIENTO_ID));
            movimientoReporte.setFecha(((java.sql.Date) row.get(Constants.FECHA)).toLocalDate());
            movimientoReporte.setTipoMovimiento((String) row.get(Constants.TIPO_MOVIMIENTO));
            movimientoReporte.setValor((Double) row.get(Constants.VALOR));
            movimientoReporte.setSaldo((Double) row.get(Constants.SALDO));

            cuentaMap.get(numeroCuenta).getMovimientos().add(movimientoReporte);
        }

        Reporte reporte = new Reporte();
        reporte.setCuentas(new ArrayList<>(cuentaMap.values()));

        return reporte;
    }
}


