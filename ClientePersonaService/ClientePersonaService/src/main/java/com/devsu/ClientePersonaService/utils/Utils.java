package com.devsu.ClientePersonaService.utils;

import com.devsu.ClientePersonaService.model.ErrorResponse;
import com.devsu.ClientePersonaService.model.clientFechas.ClientFechas;
import com.devsu.ClientePersonaService.model.reporte.Reporte;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.logging.Logger;

import static com.devsu.ClientePersonaService.utils.Constants.CLIENTE_FECHAS_JSON;
import static com.devsu.ClientePersonaService.utils.Constants.ERROR_CONVERTING_CLIENTFECHAS;

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
            log.info(CLIENTE_FECHAS_JSON + clientFechasJson);
            return clientFechasJson;
        } catch (Exception e) {
            log.warning(ERROR_CONVERTING_CLIENTFECHAS + e);
            return null;
        }
    }

    public String sendClientFechas(Long clienteId, LocalDate date1, LocalDate date2) {
        ClientFechas clientFechas = new ClientFechas();
        clientFechas.setClientId(clienteId);
        clientFechas.setFecha1(date1);
        clientFechas.setFecha2(date2);
        return convertAndSend(clientFechas);
    }

    public static Reporte parseJsonToReporte(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(json, Reporte.class);
    }

}
