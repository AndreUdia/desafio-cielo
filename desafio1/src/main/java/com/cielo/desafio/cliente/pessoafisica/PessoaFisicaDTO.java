package com.cielo.desafio.cliente.pessoafisica;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFisicaDTO {
    private String uuid;
    private String categoriaComercial;
    private String cpf;
    private String nome;
    private String email;
}
