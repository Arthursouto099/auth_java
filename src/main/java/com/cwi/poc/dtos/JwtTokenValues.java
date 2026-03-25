package com.cwi.poc.dtos;

public record JwtTokenValues(
        String accessToken,
        String refreshToken,
        Long expiresIn) {
}
