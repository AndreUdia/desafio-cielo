package com.cielo.desafio.cliente.pessoafisica;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFisicaDTO {
    private UUID uuid;
    private String categoriaComercial;
    private String cpf;
    private String nome;
    private String email;

    public PessoaFisica transformaParaObjeto(){
        return new PessoaFisica(this.uuid, this.categoriaComercial, this.cpf, this.nome, this.email);
    }
}
