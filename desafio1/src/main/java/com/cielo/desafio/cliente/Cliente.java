package com.cielo.desafio.cliente;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;


@MappedSuperclass
public abstract class Cliente {

    @Max(4)
    private String categoriaComercial;

    @Max(50)
    private String nome;

    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$")
    private String email;

}
