package com.Devsu.CuentaMovimientosService.utils;


import com.Devsu.CuentaMovimientosService.model.ErrorResponse;
import com.Devsu.CuentaMovimientosService.model.Movimiento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public ErrorResponse buildErrorResponse(String errorCode, String errorDescription) {
        ErrorResponse responseDTO = new ErrorResponse();
        responseDTO.setType(errorCode);
        responseDTO.setBody(errorDescription);
        return responseDTO;
    }

    public ResponseEntity<ErrorResponse> validateRetiro(Movimiento movimiento) {
        if (Constants.RETIRO.equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            if (movimiento.getSaldo() < movimiento.getValor()) {
                return new ResponseEntity<>(
                        buildErrorResponse(Constants.BAD_REQUEST, Constants.SALDO_NO_DISPONIBLE),
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        return null;
    }

    public Movimiento updateSaldo(Movimiento movimiento) {
        if (Constants.RETIRO.equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            movimiento.setSaldo(movimiento.getSaldo() - movimiento.getValor());
        } else if (Constants.DEPOSITO.equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            movimiento.setSaldo(movimiento.getSaldo() + movimiento.getValor());
        }
        return movimiento;
    }
}
