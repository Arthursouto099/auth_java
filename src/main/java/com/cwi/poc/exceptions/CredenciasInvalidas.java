package com.cwi.poc.exceptions;

import org.springframework.http.HttpStatus;

public class CredenciasInvalidas extends BaseException {
    public CredenciasInvalidas(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
