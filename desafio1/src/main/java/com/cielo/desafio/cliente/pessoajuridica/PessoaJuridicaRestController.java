package com.cielo.desafio.cliente.pessoajuridica;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@AllArgsConstructor
@RequestMapping("/api/clientes/pessoasjuridicas")
public class PessoaJuridicaRestController {

    private final PessoaJuridicaService service;
    private final ModelMapper modelMapper;

    private PessoaJuridicaDTO converterParaDto(PessoaJuridicaRequest pessoaJuridicaRequest){
        return this.modelMapper.map(pessoaJuridicaRequest, PessoaJuridicaDTO.class);
    }

    private PessoaJuridica converterParaEntidade(PessoaJuridicaDTO pessoaJuridicaDTO) {
        return this.modelMapper.map(pessoaJuridicaDTO, PessoaJuridica.class);
    }

    private static String validaDados(PessoaJuridicaRequest pessoaJuridicaRequest) {
        Validator validator;
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        Set<ConstraintViolation<PessoaJuridicaRequest>> constraintViolations = validator.validate(pessoaJuridicaRequest);

        if (constraintViolations.iterator().hasNext())
        {
            return constraintViolations.iterator().next().getMessage();
        }

        return "";
    }

    @GetMapping
    public ResponseEntity<List<PessoaJuridicaDtoRecord>> listarTodas(){
        return new ResponseEntity<>(this.service.listarTodas().stream().map(PessoaJuridicaDtoRecord::new).toList(), OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> buscarPorUUID(@PathVariable String uuid) {
        Optional<PessoaJuridicaDTO> pessoaJuridicaDTO = this.service.buscarDtoPorUUID(uuid);

        if (pessoaJuridicaDTO.isPresent()) {
            return new ResponseEntity<>(new PessoaJuridicaDtoRecord(pessoaJuridicaDTO.get()), OK);
        }
        return new ResponseEntity<>("Não há cliente cadastrado com o código recebido!", NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarPessoaJuridica(@RequestBody PessoaJuridicaRequest pessoaJuridicaRequest) {
        String validacoes = validaDados(pessoaJuridicaRequest);

        if (!validacoes.isEmpty()){
            return new ResponseEntity<>(validacoes, BAD_REQUEST);
        }

        PessoaJuridicaDTO pessoaJuridicaDTO = this.converterParaDto(pessoaJuridicaRequest);

        if (this.service.buscarDtoPorUUID(pessoaJuridicaDTO.getUuid()).isPresent()){
            return new ResponseEntity<>("Este cliente já possui cadastro", CONFLICT);
        }

        this.service.salvarPessoaJuridica(this.converterParaEntidade(pessoaJuridicaDTO));

        return new ResponseEntity<>("Cliente cadastrado com sucesso!", CREATED);

    }

    @PatchMapping
    public ResponseEntity<String> alterarPessoaJuridica(@RequestBody PessoaJuridicaRequest pessoaJuridicaRequest) {
        String validacoes = validaDados(pessoaJuridicaRequest);

        if (!validacoes.isEmpty()){
            return new ResponseEntity<>(validacoes, BAD_REQUEST);
        }

        Optional<PessoaJuridica> pessoaJuridica = this.service.findByUuid(pessoaJuridicaRequest.getUuid());

        if (pessoaJuridica.isPresent()){
            pessoaJuridica.get().setCnpj(pessoaJuridicaRequest.getCnpj());
            pessoaJuridica.get().setRazaoSocial(pessoaJuridicaRequest.getRazaoSocial());
            pessoaJuridica.get().setCategoriaComercial(pessoaJuridicaRequest.getCategoriaComercial());
            pessoaJuridica.get().setCpfContato(pessoaJuridicaRequest.getCpfContato());
            pessoaJuridica.get().setNome(pessoaJuridicaRequest.getNome());
            pessoaJuridica.get().setEmail(pessoaJuridicaRequest.getEmail());

            this.service.salvarPessoaJuridica(pessoaJuridica.get());

            return new ResponseEntity<>("Dados alterados com sucesso!", OK);
        }
        return new ResponseEntity<>("Cliente ainda não cadastrado!", NOT_FOUND);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> excluirElogio(@PathVariable String uuid) {
        Optional<PessoaJuridica> pessoaJuridica = this.service.findByUuid(uuid);

        if (pessoaJuridica.isPresent()) {
            this.service.excluirPessoaJuridica(uuid);
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            return new ResponseEntity<>("Não há cliente cadastrado com o código recebido!", NOT_FOUND);
        }
    }
}
