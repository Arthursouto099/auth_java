package com.cwi.poc.controller;

import com.cwi.poc.dtos.JwtTokenValues;
import com.cwi.poc.dtos.LoginRequest;
import com.cwi.poc.dtos.RefreshTokenRequest;
import com.cwi.poc.dtos.UsuarioBaseResponse;
import com.cwi.poc.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtTokenValues login(@RequestBody @Valid LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/refresh")
    public JwtTokenValues refreshToken(@RequestBody @Valid RefreshTokenRequest request) {

        return authService.renovarToken(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {

        authService.logout();
    }

    @GetMapping("/usuario/informacoes")
    public UsuarioBaseResponse getInformacoes() {

        return authService.getInformacoes();
    }
}
