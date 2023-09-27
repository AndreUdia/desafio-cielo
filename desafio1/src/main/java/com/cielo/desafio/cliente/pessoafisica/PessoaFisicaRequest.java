package com.cielo.desafio.cliente.pessoafisica;

import jakarta.validation.constraints.Max;
import lombok.*;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFisicaRequest {
    private UUID uuid;

    @Max(4)
    private String categoriaComercial;

    @Pattern(regexp = "\"^[0-9]{11}$\"")
    private String cpf;

    @Max(50)
    private String nome;

    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$")
    private String email;
}
