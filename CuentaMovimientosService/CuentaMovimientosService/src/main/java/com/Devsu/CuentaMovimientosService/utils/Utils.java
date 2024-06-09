package com.Devsu.CuentaMovimientosService.utils;


import com.Devsu.CuentaMovimientosService.model.ErrorResponse;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public ErrorResponse buildErrorResponse(String errorCode, String errorDescription) {
        ErrorResponse responseDTO = new ErrorResponse();
        responseDTO.setType(errorCode);
        responseDTO.setBody(errorDescription);
        return responseDTO;
    }
}
