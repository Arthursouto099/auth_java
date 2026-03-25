package com.cwi.poc.exceptions;

import org.springframework.http.HttpStatus;

public class RecursoNaoEncontradoException extends BaseException {
    public RecursoNaoEncontradoException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
