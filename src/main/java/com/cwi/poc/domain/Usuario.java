package com.cwi.poc.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario extends BaseEntity {

    private String nome;
    private String email;
    private String cpf;
    private String senha;
    private Boolean ativo;
    private LocalDate dataNascimento;
    private LocalDateTime dataUltimoAcesso;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permissao> permissoes = new ArrayList<>();

    public List<String> retornarPermissoesEmString() {

        return this.permissoes
                .stream()
                .map(Permissao::getNome)
                .toList();
    }

    public void adicionarPermissao(Permissao permissao) {

        this.permissoes.add(permissao);
        permissao.setUsuario(this);
    }

}
