package com.cielo.desafio.cliente;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@MappedSuperclass
@Getter
@Setter
public abstract class Cliente {

    private UUID uuid;

    private String categoriaComercial;

    private String nome;

    private String email;

}
