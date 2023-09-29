package com.cielo.desafio.cliente;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFisicaDTO {
    private String uuid;
    private String categoriaComercial;
    private String cadastroNacional;
    private String nome;
    private String email;
    private String cpfContato;
}
