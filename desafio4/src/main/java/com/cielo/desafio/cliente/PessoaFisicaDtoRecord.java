package com.cielo.desafio.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PessoaFisicaDtoRecord(String uuid, String categoriaComercial, String cadastroNacional, String nome, String email) {

    public PessoaFisicaDtoRecord(PessoaFisicaDTO pessoaFisicaDTO){
        this(pessoaFisicaDTO.getUuid(), pessoaFisicaDTO.getCategoriaComercial(), pessoaFisicaDTO.getCadastroNacional(),pessoaFisicaDTO.getNome(), pessoaFisicaDTO.getEmail());
    }
}
