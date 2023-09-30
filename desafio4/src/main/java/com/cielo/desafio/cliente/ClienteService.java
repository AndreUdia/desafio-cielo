package com.cielo.desafio.cliente;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ModelMapper modelMapper;

    private PessoaFisicaDTO converterPfParaDto(Cliente cliente){
        return this.modelMapper.map(cliente, PessoaFisicaDTO.class);
    }

    private PessoaJuridicaDTO converterPjParaDto(Cliente cliente){
        return this.modelMapper.map(cliente, PessoaJuridicaDTO.class);
    }

    private ClienteDTO converterParaDto(Cliente cliente){
        return this.modelMapper.map(cliente, ClienteDTO.class);
    }

    public List<PessoaFisicaDtoRecord> buscarTodasPessoasFisicas(){
        return this.repository.findAll().stream()
                .filter(cliente -> cliente.getTipoCliente().equals(TipoCliente.PF))
                .map(this::converterPfParaDto)
                .map(PessoaFisicaDtoRecord::new).toList();
    }

    public List<PessoaJuridicaDtoRecord> buscarTodasPessoasJuridicas(){
        return this.repository.findAll().stream()
                .filter(cliente -> cliente.getTipoCliente().equals(TipoCliente.PJ))
                .map(this::converterPjParaDto)
                .map(PessoaJuridicaDtoRecord::new).toList();
    }

    public List<ClienteDTO> listarTodosClientes(){
        return this.repository.findAll().stream().map(this::converterParaDto).toList();
    }

    public void salvarCliente(Cliente cliente, TipoCliente tipoCliente) {
        cliente.setTipoCliente(tipoCliente);
        this.repository.save(cliente);
    }

    public void excluirCliente(String uuid) {
        Optional<Cliente> cliente = this.findByUuid(uuid);
        cliente.ifPresent(cli -> this.repository.deleteById(cli.getId()));
    }

    public Optional<PessoaFisicaDTO> buscarDtoPfPorUUID(String uuid) {
        Optional<Cliente> cliente = this.repository.findByUuid(uuid);
        if (cliente.isPresent() && cliente.get().getTipoCliente().equals(TipoCliente.PF)) {
            return cliente.map(this::converterPfParaDto);
        }
        return Optional.empty();
    }

    public Optional<PessoaJuridicaDTO> buscarDtoPjPorUUID(String uuid) {
        Optional<Cliente> cliente = this.repository.findByUuid(uuid);
        if (cliente.isPresent() && cliente.get().getTipoCliente().equals(TipoCliente.PJ)) {
            return cliente.map(this::converterPjParaDto);
        }
        return Optional.empty();
    }

    public Optional<Cliente> findByUuid(String uuid) {
        return this.repository.findByUuid(uuid);
    }
}
