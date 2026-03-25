package com.cwi.poc.dtos;

public record FieldErrorResponse(
        String field,
        String message
) {
}
