package com.devsu.ClientePersonaService.utils;

import com.devsu.ClientePersonaService.model.ErrorResponse;
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
