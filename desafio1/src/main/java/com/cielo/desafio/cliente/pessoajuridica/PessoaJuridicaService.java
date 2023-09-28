package com.cielo.desafio.cliente.pessoajuridica;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PessoaJuridicaService {

    private final PessoaJuridicaRepository repository;
    private final ModelMapper modelMapper;

    private PessoaJuridicaDTO converterParaDto(PessoaJuridica pessoaJuridica){
        return this.modelMapper.map(pessoaJuridica, PessoaJuridicaDTO.class);
    }

    public List<PessoaJuridicaDTO> listarTodas(){
        return this.repository.findAll().stream().map(this::converterParaDto).toList();
    }

    public void salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.repository.save(pessoaJuridica);
    }

    public void excluirPessoaJuridica(String uuid) {
        Optional<PessoaJuridica> pessoaJuridica = this.findByUuid(uuid);
        pessoaJuridica.ifPresent(pf -> this.repository.deleteById(pf.getId()));
    }

    public Optional<PessoaJuridicaDTO> buscarDtoPorUUID(String uuid) {
        return this.repository.findByUuid(uuid).map(this::converterParaDto);
    }

    public Optional<PessoaJuridica> findByUuid(String uuid) {
        return this.repository.findByUuid(uuid);
    }

}
