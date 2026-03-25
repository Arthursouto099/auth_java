package com.cwi.poc.service;

import com.cwi.poc.dtos.JwtTokenValues;
import com.cwi.poc.dtos.LoginRequest;
import com.cwi.poc.dtos.RefreshTokenRequest;
import com.cwi.poc.exceptions.CredenciasInvalidas;
import com.cwi.poc.exceptions.RecursoNaoEncontradoException;
import com.cwi.poc.repository.RefreshTokenRepository;
import com.cwi.poc.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public JwtTokenValues login(LoginRequest loginRequest) {

        final var usuario = usuarioRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado."));

        if(!passwordEncoder.matches(loginRequest.senha(), usuario.getSenha())) {
            throw new CredenciasInvalidas("Senha Incorreta, credencias invalidas");
        }

        return tokenService.gerarToken(usuario);
    }

    public JwtTokenValues renovarToken(RefreshTokenRequest refreshTokenRequest) {

        final var token = refreshTokenRepository
                .findByToken(refreshTokenRequest.refreshTokenId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("RefreshToken não encontrado."));

        if(token.getExpiracao().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new CredenciasInvalidas("Refresh token expirado.");
        }

        return tokenService.gerarToken(token.getUsuario());
    }


}
