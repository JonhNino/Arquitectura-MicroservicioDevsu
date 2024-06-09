package com.devsu.ClientePersonaService.exception;

public class CustomException extends RuntimeException {

    public CustomException(String errorMessage) {
        super(errorMessage);
    }

    public static class InvalidInputException extends Exception {
        public InvalidInputException(String errorMessage) {
            super(errorMessage);
        }
    }

}