package com.cwi.poc.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RefreshTokenRequest(
        @NotNull(message = "UUID do token é necessario")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-.*", message = "Formato de UUID inválido")
        String refreshTokenId
) {
}
