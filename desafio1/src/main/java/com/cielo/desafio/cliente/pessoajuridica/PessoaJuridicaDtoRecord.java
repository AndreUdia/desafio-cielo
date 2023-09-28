package com.cielo.desafio.cliente.pessoajuridica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PessoaJuridicaDtoRecord(String uuid, String cnpj,String razaoSocial,String categoriaComercial,
                                        String cpfContato, String nome, String email) {
    public PessoaJuridicaDtoRecord(PessoaJuridicaDTO pessoaJuridicaDTO){
        this(pessoaJuridicaDTO.getUuid(),
                pessoaJuridicaDTO.getCnpj(),
                pessoaJuridicaDTO.getRazaoSocial(),
                pessoaJuridicaDTO.getCategoriaComercial(),
                pessoaJuridicaDTO.getCpfContato(),
                pessoaJuridicaDTO.getNome(),
                pessoaJuridicaDTO.getEmail());
    }
}
