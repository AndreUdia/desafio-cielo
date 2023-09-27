package com.cielo.desafio.cliente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@MappedSuperclass
@Getter
@Setter
public abstract class Cliente {

    private String uuid;

    private String categoriaComercial;

    private String nome;

    private String email;

}
