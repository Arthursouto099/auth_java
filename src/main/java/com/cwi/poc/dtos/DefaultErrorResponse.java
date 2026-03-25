package com.cwi.poc.dtos;

import java.time.LocalDateTime;

public record DefaultErrorResponse(
        String message,
        Integer status,
        LocalDateTime timestamp
) {
}
