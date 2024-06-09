package com.devsu.ClientePersonaService.utils;

import com.devsu.ClientePersonaService.ClientePersonaServiceApplication;
import com.devsu.ClientePersonaService.model.ErrorResponse;
import com.devsu.ClientePersonaService.model.clientFechas.ClientFechas;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class Utils {
    private static final Logger log = Logger.getLogger(Utils.class.getName());
    private final ObjectMapper objectMapper;
  //  private final Publisher publisher;

    public Utils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ErrorResponse buildErrorResponse(String errorCode, String errorDescription) {
        ErrorResponse responseDTO = new ErrorResponse();
        responseDTO.setType(errorCode);
        responseDTO.setBody(errorDescription);
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

}
