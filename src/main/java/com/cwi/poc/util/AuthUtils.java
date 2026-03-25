package com.cwi.poc.util;

import com.cwi.poc.domain.Usuario;
import com.cwi.poc.exceptions.RecursoNaoEncontradoException;
import com.cwi.poc.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final UsuarioRepository usuarioRepository;

    private String getEmail() {

        final var auth = SecurityContextHolder.getContext().getAuthentication();
        final var jwt = (Jwt) auth.getCredentials();

        return jwt.getClaim("email");
    }

    public Usuario getUsuario() {

        return usuarioRepository.findByEmail(this.getEmail())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado"));
    }
}
