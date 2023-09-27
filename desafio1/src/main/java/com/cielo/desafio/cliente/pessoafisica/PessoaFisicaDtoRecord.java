package com.cielo.desafio.cliente.pessoafisica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PessoaFisicaDtoRecord(UUID uuid, String categoriaComercial, String cpf, String nome, String email) {

    public PessoaFisicaDtoRecord(PessoaFisicaDTO pessoaFisicaDTO){
        this(pessoaFisicaDTO.getUuid(), pessoaFisicaDTO.getCategoriaComercial(), pessoaFisicaDTO.getCpf(),pessoaFisicaDTO.getNome(), pessoaFisicaDTO.getEmail());
    }
}
