package com.cwi.poc.service;

import com.cwi.poc.domain.RefreshToken;
import com.cwi.poc.dtos.JwtTokenValues;
import com.cwi.poc.dtos.LoginRequest;
import com.cwi.poc.dtos.RefreshTokenRequest;
import com.cwi.poc.dtos.UsuarioBaseResponse;
import com.cwi.poc.exceptions.CredenciasInvalidas;
import com.cwi.poc.exceptions.RecursoNaoEncontradoException;
import com.cwi.poc.mapper.UsuarioMapper;
import com.cwi.poc.repository.RefreshTokenRepository;
import com.cwi.poc.repository.UsuarioRepository;
import com.cwi.poc.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthUtils authUtils;

    public JwtTokenValues login(LoginRequest loginRequest) {

        final var usuario = usuarioRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado."));

        if(!passwordEncoder.matches(loginRequest.senha(), usuario.getSenha())) {
            throw new CredenciasInvalidas("Senha Incorreta, credencias invalidas");
        }

        return tokenService.gerarToken(usuario);
    }

    private RefreshToken findRefreshTokenByUUID(String tokenId) {

        return refreshTokenRepository
                .findByToken(tokenId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("RefreshToken não encontrado."));
    }

    public JwtTokenValues renovarToken(RefreshTokenRequest refreshTokenRequest) {

        final var refresh = findRefreshTokenByUUID(refreshTokenRequest.refreshTokenId());

        if(refresh.getExpiracao().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refresh);
            throw new CredenciasInvalidas("Refresh token expirado.");
        }

        return tokenService.gerarToken(refresh.getUsuario());
    }

    @Transactional
    public void logout() {

        refreshTokenRepository.deleteByUsuario(
                authUtils.getUsuario()
        );
    }

    @Transactional(readOnly = true)
    public UsuarioBaseResponse getInformacoes() {

        return UsuarioMapper.toBasicResponse(
                authUtils.getUsuario()
        );
    }



}
