package com.cwi.poc.repository;

import com.cwi.poc.domain.RefreshToken;
import com.cwi.poc.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken ,Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    void  deleteByUsuario(Usuario usuario);

}
