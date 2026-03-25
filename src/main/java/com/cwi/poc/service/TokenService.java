package com.cwi.poc.service;

import com.cwi.poc.domain.Permissao;
import com.cwi.poc.domain.RefreshToken;
import com.cwi.poc.domain.Usuario;
import com.cwi.poc.dtos.JwtTokenValues;
import com.cwi.poc.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final Long EXPIRES_IN = 600L;
    private static final Integer TOKEN_DAYS_EXPIRATION = 7;

    private String salvarRefreshToken(Usuario usuario) {

        refreshTokenRepository.deleteByUsuario(usuario);
        refreshTokenRepository.flush();

        final var valorTokenRefresh = UUID.randomUUID().toString();
        final var token = RefreshToken.builder()
                .usuario(usuario)
                .token(valorTokenRefresh)
                .expiracao(LocalDateTime.now().plusDays(TOKEN_DAYS_EXPIRATION))
                .build();
        
        refreshTokenRepository.save(token);
        return valorTokenRefresh;
    }

    @Transactional
    public JwtTokenValues gerarToken(Usuario usuario) {

        JwtClaimsSet jwt = JwtClaimsSet.builder()
                .issuer("poc")
                .subject(usuario.getId().toString())
                .expiresAt(Instant.now().plusSeconds(EXPIRES_IN))
                .issuedAt(Instant.now())
                .claim("email", usuario.getEmail())
                .claim("scope", usuario.retornarPermissoesEmString(usuario.getPermissoes()))
                .build();

        final var accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwt)).getTokenValue();
        final var uuidRefreshToken = salvarRefreshToken(usuario);

        return new JwtTokenValues(accessToken, uuidRefreshToken, EXPIRES_IN);
    }


}
