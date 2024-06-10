package com.devsu.ClientePersonaService.utils;

import com.devsu.ClientePersonaService.ClientePersonaServiceApplication;
import com.devsu.ClientePersonaService.model.ErrorResponse;
import com.devsu.ClientePersonaService.model.clientFechas.ClientFechas;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.logging.Logger;

@Component
public class Utils {
    private static final Logger log = Logger.getLogger(Utils.class.getName());
    private final ObjectMapper objectMapper;

    public Utils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static ErrorResponse buildErrorResponse(String errorCode, String errorDescription, Object detalle) {
        ErrorResponse responseDTO = new ErrorResponse();
        responseDTO.setType(errorCode);
        responseDTO.setBody(errorDescription);
        responseDTO.setDetalle(detalle);
        return responseDTO;
    }

    public String convertAndSend(ClientFechas clientFechas) {
        try {
            String clientFechasJson = objectMapper.writeValueAsString(clientFechas);
            log.info("ClientFechas JSON: {}"+ clientFechasJson);
          //  this.publisher.send(clientFechasJson);
            return clientFechasJson;
        } catch (Exception e) {
            log.warning("Error converting ClientFechas to JSON"+ e);
            return null;
        }
    }

    public String sendClientFechas(Long clienteId, LocalDate date1, LocalDate date2){
        ClientFechas clientFechas = new ClientFechas();
        clientFechas.setClientId(clienteId);
        clientFechas.setFecha1(date1);
        clientFechas.setFecha2(date2);
        return convertAndSend(clientFechas);
    }

}
