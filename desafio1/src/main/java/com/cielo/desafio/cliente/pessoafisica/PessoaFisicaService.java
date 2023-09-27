package com.cielo.desafio.cliente.pessoafisica;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PessoaFisicaService {

    private final PessoaFisicaRepository repository;
    private final ModelMapper modelMapper;

    private PessoaFisicaDTO converterParaDto(PessoaFisica pessoaFisica){
        return this.modelMapper.map(pessoaFisica, PessoaFisicaDTO.class);
    }

    public List<PessoaFisicaDTO> listarTodas(){
        return this.repository.findAll().stream().map(this::converterParaDto).toList();
    }

    public void salvarPessoaFisica(PessoaFisica pessoaFisica) {
        this.repository.save(pessoaFisica);
    }

    public void excluirPessoaFisica(Long id) {
        this.repository.deleteById(id);
    }

    public Optional<PessoaFisicaDTO> buscarDtoPorUUID(String uuid) {
        return this.repository.findByUuid(uuid).map(this::converterParaDto);
    }

    public Optional<PessoaFisica> findByUuid(String uuid) {
        return this.repository.findByUuid(uuid);
    }
}
