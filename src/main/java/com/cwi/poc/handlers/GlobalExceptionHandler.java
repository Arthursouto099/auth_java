package com.cwi.poc.handlers;

import com.cwi.poc.dtos.BadRequestErrorResponse;
import com.cwi.poc.dtos.DefaultErrorResponse;
import com.cwi.poc.dtos.FieldErrorResponse;
import com.cwi.poc.exceptions.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private List<FieldErrorResponse> extrairErros(MethodArgumentNotValidException ex) {

        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorResponse(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

        return  ResponseEntity.status(ex.getStatusCode()).body(
                new BadRequestErrorResponse(
                        ex.getMessage(),
                        ex.getStatusCode().value(),
                        LocalDateTime.now(),
                        extrairErros(ex)
                )
        );

    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<DefaultErrorResponse> handleBasicResponse(BaseException ex) {

        return ResponseEntity.status(ex.getHttpStatusCode()).body(
                new DefaultErrorResponse(
                        ex.getMessage(),
                        ex.getHttpStatusCodeToInteger(),
                        LocalDateTime.now()
                )
        );
    }


}
