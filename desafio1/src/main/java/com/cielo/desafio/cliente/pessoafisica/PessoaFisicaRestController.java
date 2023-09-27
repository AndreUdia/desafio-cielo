package com.cielo.desafio.cliente.pessoafisica;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/clientes/pessoasfisicas")
public class PessoaFisicaRestController {

    private final PessoaFisicaService service;
    private final ModelMapper modelMapper;

    private PessoaFisicaDTO converterParaDto(PessoaFisicaRequest pessoaFisicaRequest){
        return this.modelMapper.map(pessoaFisicaRequest, PessoaFisicaDTO.class);
    }

    private PessoaFisica converterParaEntidade(PessoaFisicaDTO pessoaFisicaDTO) {
        return this.modelMapper.map(pessoaFisicaDTO, PessoaFisica.class);
    }

    @GetMapping
    public ResponseEntity<List<PessoaFisicaDtoRecord>> listarTodas(){
        return new ResponseEntity<>(this.service.listarTodas().stream().map(PessoaFisicaDtoRecord::new).toList(), OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> buscarPorUUID(@PathVariable UUID uuid) {
        Optional<PessoaFisicaDTO> pessoaFisicaDTO = this.service.buscarDtoPorUUID(uuid);

        if (pessoaFisicaDTO.isPresent()) {
            return new ResponseEntity<>(new PessoaFisicaDtoRecord(pessoaFisicaDTO.get()), OK);
        }
        return new ResponseEntity<>("Não há cliente cadastrado com o código recebido!", NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarPessoaFisica(@RequestBody PessoaFisicaRequest pessoaFisicaRequest) {
        PessoaFisicaDTO pessoaFisicaDTO = this.converterParaDto(pessoaFisicaRequest);

        if (this.service.buscarDtoPorUUID(pessoaFisicaDTO.getUuid()).isPresent()){
            return new ResponseEntity<>("Este cliente já possui cadastro", CONFLICT);
        }

        this.service.salvarPessoaFisica(this.converterParaEntidade(pessoaFisicaDTO));

        return new ResponseEntity<>("Cliente cadastrado com sucesso!", CREATED);

    }

    @PatchMapping
    public ResponseEntity<String> alterarPessoaFisica(@RequestBody PessoaFisicaRequest pessoaFisicaRequest) {
        System.out.println(pessoaFisicaRequest.toString());
        Optional<PessoaFisica> pessoaFisica = this.service.findByUuid(pessoaFisicaRequest.getUuid());

        if (pessoaFisica.isPresent()){
            pessoaFisica.get().setCategoriaComercial(pessoaFisicaRequest.getCategoriaComercial());
            pessoaFisica.get().setCpf(pessoaFisicaRequest.getCpf());
            pessoaFisica.get().setNome(pessoaFisicaRequest.getNome());
            pessoaFisica.get().setEmail(pessoaFisicaRequest.getEmail());
            this.service.salvarPessoaFisica(pessoaFisica.get());

            return new ResponseEntity<>("Dados alterados com sucesso!", OK);
        }
        return new ResponseEntity<>("Cliente ainda não cadastrado!", NOT_FOUND);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> excluirElogio(@PathVariable UUID uuid) {
        Optional<PessoaFisica> pessoaFisica = this.service.findByUuid(uuid);

        if (pessoaFisica.isPresent()) {
            this.service.excluirPessoaFisica(pessoaFisica.get().getId());
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            return new ResponseEntity<>("Não há cliente cadastrado com o código recebido!", NOT_FOUND);
        }
    }


}
