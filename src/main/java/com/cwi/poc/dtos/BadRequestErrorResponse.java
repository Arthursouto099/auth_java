package com.cwi.poc.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record BadRequestErrorResponse(
        String message,
        Integer status,
        LocalDateTime timestamp,
        List<FieldErrorResponse> errors
) {

}
