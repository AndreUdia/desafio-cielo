package com.cielo.desafio.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PessoaJuridicaDtoRecord(String uuid, String cadastroNacional,String razaoSocial,String categoriaComercial,
                                        String cpfContato, String nome, String email) {
    public PessoaJuridicaDtoRecord(PessoaJuridicaDTO pessoaJuridicaDTO){
        this(pessoaJuridicaDTO.getUuid(),
                pessoaJuridicaDTO.getCadastroNacional(),
                pessoaJuridicaDTO.getRazaoSocial(),
                pessoaJuridicaDTO.getCategoriaComercial(),
                pessoaJuridicaDTO.getCpfContato(),
                pessoaJuridicaDTO.getNome(),
                pessoaJuridicaDTO.getEmail());
    }
}
