package com.cielo.desafio.cliente;

import com.cielo.desafio.utils.FilaCliente;
import io.awspring.cloud.sqs.annotation.SqsListener;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessageChannel;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.*;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/clientes")
public class ClienteRestController {

    private final ClienteService service;
    private final ModelMapper modelMapper;
    private final QueueMessagingTemplate queueMessagingTemplate;

    private static String sqsEndpoint = "https://sqs.us-east-1.amazonaws.com/165819780326/desafio-cielo-sqs.fifo";

    public static FilaCliente filaCliente = new FilaCliente(100);


    private PessoaFisicaDTO converterPfParaDto(PessoaFisicaRequest pessoaFisicaRequest){
        return this.modelMapper.map(pessoaFisicaRequest, PessoaFisicaDTO.class);
    }

    private PessoaJuridicaDTO converterPjParaDto(PessoaJuridicaRequest pessoaJuridicaRequest){
        return this.modelMapper.map(pessoaJuridicaRequest, PessoaJuridicaDTO.class);
    }

    private Cliente converterParaEntidade(PessoaFisicaDTO pessoaFisicaDTO) {
        return this.modelMapper.map(pessoaFisicaDTO, Cliente.class);
    }

    private static String validaDados(ClienteRequest clienteRequest) {
        Validator validator;
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        Set<ConstraintViolation<ClienteRequest>> constraintViolations = validator.validate(clienteRequest);

        if (constraintViolations.iterator().hasNext())
        {
            return constraintViolations.iterator().next().getMessage();
        }

        return "";
    }

    @GetMapping("/pessoasfisicas")
    public ResponseEntity<List<PessoaFisicaDtoRecord>> listarTodas(){
        return new ResponseEntity<>(this.service.buscarTodasPessoasFisicas(), OK);
    }

    @GetMapping("/pessoasfisicas/{uuid}")
    public ResponseEntity<?> buscarPfPorUUID(@PathVariable String uuid) {
        Optional<PessoaFisicaDTO> pessoaFisicaDTO = this.service.buscarDtoPfPorUUID(uuid);

        if (pessoaFisicaDTO.isPresent()) {
            return new ResponseEntity<>(new PessoaFisicaDtoRecord(pessoaFisicaDTO.get()), OK);
        }
        return new ResponseEntity<>("Não há cliente cadastrado com o código recebido!", NOT_FOUND);
    }

    @PostMapping("/pessoasfisicas")
    public ResponseEntity<String> cadastrarPessoaFisica(@RequestBody PessoaFisicaRequest pessoaFisicaRequest) {
        String validacoes = validaDados(pessoaFisicaRequest);

        if (!validacoes.isEmpty()){
            return new ResponseEntity<>(validacoes, BAD_REQUEST);
        }

        PessoaFisicaDTO pessoaFisicaDTO = this.converterPfParaDto(pessoaFisicaRequest);

        if (this.service.buscarDtoPfPorUUID(pessoaFisicaDTO.getUuid()).isPresent()){
            return new ResponseEntity<>("Este cliente já possui cadastro", CONFLICT);
        }

        Cliente clienteParaSalvar = this.converterParaEntidade(pessoaFisicaDTO);

        this.service.salvarCliente(clienteParaSalvar, TipoCliente.PF);

        this.enviarClienteParaFilaSqs(clienteParaSalvar);

        return new ResponseEntity<>("Cliente cadastrado com sucesso!", CREATED);

    }

    @PatchMapping("/pessoasfisicas")
    public ResponseEntity<String> alterarPessoaFisica(@RequestBody PessoaFisicaRequest pessoaFisicaRequest) {
        String validacoes = validaDados(pessoaFisicaRequest);

        if (!validacoes.isEmpty()){
            return new ResponseEntity<>(validacoes, BAD_REQUEST);
        }

        Optional<Cliente> cliente = this.service.findByUuid(pessoaFisicaRequest.getUuid());

        if (cliente.isPresent()){
            cliente.get().setCategoriaComercial(pessoaFisicaRequest.getCategoriaComercial());
            cliente.get().setCadastroNacional(pessoaFisicaRequest.getCadastroNacional());
            cliente.get().setNome(pessoaFisicaRequest.getNome());
            cliente.get().setEmail(pessoaFisicaRequest.getEmail());

            this.service.salvarCliente(cliente.get(), TipoCliente.PF);

            this.enviarClienteParaFilaSqs(cliente.get());

            return new ResponseEntity<>("Dados alterados com sucesso!", OK);
        }
        return new ResponseEntity<>("Cliente ainda não cadastrado!", NOT_FOUND);
    }

    @GetMapping("/pessoasjuridicas")
    public ResponseEntity<List<PessoaJuridicaDtoRecord>> listarTodasPj(){
        return new ResponseEntity<>(this.service.buscarTodasPessoasJuridicas(), OK);
    }

    @GetMapping("/pessoasjuridicas/{uuid}")
    public ResponseEntity<?> buscarPjPorUUID(@PathVariable String uuid) {
        Optional<PessoaJuridicaDTO> pessoaJuridicaDTO = this.service.buscarDtoPjPorUUID(uuid);

        if (pessoaJuridicaDTO.isPresent()) {
            return new ResponseEntity<>(new PessoaJuridicaDtoRecord(pessoaJuridicaDTO.get()), OK);
        }
        return new ResponseEntity<>("Não há cliente cadastrado com o código recebido!", NOT_FOUND);
    }

    @PostMapping("/pessoasjuridicas")
    public ResponseEntity<String> cadastrarPessoaJuridica(@RequestBody PessoaJuridicaRequest pessoaJuridicaRequest) {
        String validacoes = validaDados(pessoaJuridicaRequest);

        if (!validacoes.isEmpty()){
            return new ResponseEntity<>(validacoes, BAD_REQUEST);
        }

        PessoaJuridicaDTO pessoaJuridicaDTO = this.converterPjParaDto(pessoaJuridicaRequest);

        if (this.service.buscarDtoPjPorUUID(pessoaJuridicaDTO.getUuid()).isPresent()){
            return new ResponseEntity<>("Este cliente já possui cadastro", CONFLICT);
        }

        Cliente clienteParaSalvar = this.modelMapper.map(pessoaJuridicaDTO, Cliente.class);

        this.service.salvarCliente(clienteParaSalvar, TipoCliente.PJ);

        this.enviarClienteParaFilaSqs(clienteParaSalvar);

        return new ResponseEntity<>("Cliente cadastrado com sucesso!", CREATED);

    }

    @PatchMapping("/pessoasjuridicas")
    public ResponseEntity<String> alterarPessoaJuridica(@RequestBody PessoaJuridicaRequest pessoaJuridicaRequest) {
        String validacoes = validaDados(pessoaJuridicaRequest);

        if (!validacoes.isEmpty()){
            return new ResponseEntity<>(validacoes, BAD_REQUEST);
        }

        Optional<Cliente> cliente = this.service.findByUuid(pessoaJuridicaRequest.getUuid());

        if (cliente.isPresent()){
            cliente.get().setCategoriaComercial(pessoaJuridicaRequest.getCategoriaComercial());
            cliente.get().setCadastroNacional(pessoaJuridicaRequest.getCadastroNacional());
            cliente.get().setNome(pessoaJuridicaRequest.getNome());
            cliente.get().setEmail(pessoaJuridicaRequest.getEmail());

            this.service.salvarCliente(cliente.get(), TipoCliente.PJ);

            this.enviarClienteParaFilaSqs(cliente.get());

            return new ResponseEntity<>("Dados alterados com sucesso!", OK);
        }
        return new ResponseEntity<>("Cliente ainda não cadastrado!", NOT_FOUND);
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<String> excluirPessoaJuridica(@PathVariable String uuid) {
        Optional<Cliente> cliente = this.service.findByUuid(uuid);

        if (cliente.isPresent()) {
            this.service.excluirCliente(uuid);
            return new ResponseEntity<>(NO_CONTENT);
        } else {
            return new ResponseEntity<>("Não há cliente cadastrado com o código recebido!", NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes(){
        return new ResponseEntity<>(this.service.listarTodosClientes(), OK);
    }

    @GetMapping("/filas/retirada")
    public ResponseEntity<?> proximoCliente() {
        Cliente cliente = this.queueMessagingTemplate.receiveAndConvert("desafio-cielo-sqs.fifo", Cliente.class);

        if (cliente == null){
            return new ResponseEntity<>("Não há mais clientes na fila", NOT_FOUND);
        }

        if (cliente.getTipoCliente().equals(TipoCliente.PF)){
            return new ResponseEntity<>(this.modelMapper.map(cliente, PessoaJuridicaDTO.class), OK);
        }

        if (cliente.getTipoCliente().equals(TipoCliente.PJ)){
            return new ResponseEntity<>(this.modelMapper.map(cliente, PessoaJuridicaDTO.class), OK);
        }

        return new ResponseEntity<>("Houve um erro interno no servidor", INTERNAL_SERVER_ERROR);

    }

    private void adicionarClienteNaFila(Cliente cliente) {
        filaCliente.enfileirar(cliente);
    }

    private void enviarClienteParaFilaSqs(Cliente cliente){
        Map<String, Object> headers = new HashMap<>();
        headers.put(SqsMessageHeaders.SQS_GROUP_ID_HEADER, "id-1");
        headers.put(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER, "id-2");
        queueMessagingTemplate.convertAndSend(sqsEndpoint, cliente, headers);
    }
}
