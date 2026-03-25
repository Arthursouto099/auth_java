package com.cwi.poc.mapper;

import com.cwi.poc.domain.Usuario;
import com.cwi.poc.dtos.UsuarioBaseResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioMapper {

    public static UsuarioBaseResponse toBasicResponse(Usuario usuario) {

        return new UsuarioBaseResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.isAtivo(),
                usuario.getCriadoEm(),
                usuario.getEditadoEm(),
                usuario.retornarPermissoesEmString()
        );
    }
}
