package com.cwi.poc.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "editado_em")
    private LocalDateTime editadoEm;

    @PrePersist
    public void prePersist() {
        this.editadoEm = LocalDateTime.now();
        this.criadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        this.editadoEm = LocalDateTime.now();
    }




}
