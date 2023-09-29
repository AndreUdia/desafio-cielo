package com.cielo.desafio.cliente;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PessoaJuridicaDTO {
    private String uuid;
    private String cadastroNacional;
    private String razaoSocial;
    private String categoriaComercial;
    private String cpfContato;
    private String nome;
    private String email;
}
