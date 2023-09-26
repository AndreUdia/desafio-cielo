package com.cielo.desafio.cliente;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@MappedSuperclass
public abstract class Cliente {

    private String categoriaComercial;

    private String nome;
    private String email;

}
