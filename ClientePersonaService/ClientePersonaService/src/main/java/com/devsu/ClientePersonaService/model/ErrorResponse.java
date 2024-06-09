package com.devsu.ClientePersonaService.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private String type;
    private String body;
}