package com.devsu.ClientePersonaService.exception;

public class EmptyReportException extends RuntimeException {
    public EmptyReportException(String message) {
        super(message);
    }
}