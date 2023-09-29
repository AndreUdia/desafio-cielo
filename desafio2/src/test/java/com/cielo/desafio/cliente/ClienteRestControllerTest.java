package com.cielo.desafio.cliente;

import com.cielo.desafio.utils.FilaCliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class ClienteRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ClienteRestController clienteRestController;

    @Test
    void deveListarTodosOsClientes() throws Exception {
        Mockito.when(this.clienteService.listarTodosClientes())
                .thenReturn(new ArrayList<>(){});

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk());
    }

    @Test
    void deveValidarQuandoEncontraUmaPessoaFisicaPeloUUID() throws Exception {
        Mockito.when(clienteService.buscarDtoPfPorUUID("eA53B7bC-dB84-87D4-2734-545Ed3c079fE"))
                .thenReturn(Optional.of(new PessoaFisicaDTO()));

        mockMvc.perform(get("/api/clientes/pessoasfisicas/{uuid}",
                        "eA53B7bC-dB84-87D4-2734-545Ed3c079fE")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void deveValidarQuandoEncontraUmaPessoaJuridicaPeloUUID() throws Exception {
        Mockito.when(clienteService.buscarDtoPjPorUUID("eA53B7bC-dB84-87D4-2734-545Ed3c079fE"))
                .thenReturn(Optional.of(new PessoaJuridicaDTO()));

        mockMvc.perform(get("/api/clientes/pessoasjuridicas/{uuid}",
                        "eA53B7bC-dB84-87D4-2734-545Ed3c079fE")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void deveValidarQuandoNaoEncontraUmaPessoaFisicaPeloUUID() throws Exception {
        Mockito.when(clienteService.buscarDtoPfPorUUID("eA53B7bC-dB84-87D4-2734-545Ed3c079fE"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/pessoasfisicas/{uuid}",
                        "eA53B7bC-dB84-87D4-2734-545Ed3c079fE")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveValidarQuandoNaoEncontraUmaPessoaJuridicaPeloUUID() throws Exception {
        Mockito.when(clienteService.buscarDtoPjPorUUID("eA53B7bC-dB84-87D4-2734-545Ed3c079fE"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/pessoasjuridicas/{uuid}",
                        "eA53B7bC-dB84-87D4-2734-545Ed3c079fE")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarUmaPessoaFisica() throws Exception {
        PessoaFisicaRequest pessoaFisicaRequest = new PessoaFisicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "6238",
                "57373714912",
                "KFzrOJhyFr",
                "email@email.com"
        );

        mockMvc.perform(post("/api/clientes/pessoasfisicas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoaFisicaRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveCriarUmaPessoaJuridica() throws Exception {
        PessoaJuridicaRequest pessoaJuridicaRequest = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "1234",
                "57373714912",
                "nome",
                "email@email.com"
        );

        mockMvc.perform(post("/api/clientes/pessoasjuridicas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoaJuridicaRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveDarConflitoAoCriarUmaPessoaFisica() throws Exception {
        PessoaFisicaRequest pessoaFisicaRequest = new PessoaFisicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "6238",
                "57373714912",
                "KFzrOJhyFr",
                "email@email.com"
        );

        Mockito.when(this.clienteService.buscarDtoPfPorUUID("eA53B7bC-dB84-87D4-2734-545Ed3c079fE"))
                .thenReturn(Optional.of(new PessoaFisicaDTO()));

        mockMvc.perform(post("/api/clientes/pessoasfisicas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoaFisicaRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void deveDarConflitoAoCriarUmaPessoaJuridica() throws Exception {
        PessoaJuridicaRequest pessoaJuridicaRequest = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "1234",
                "57373714912",
                "nome",
                "email@email.com"
        );

        Mockito.when(this.clienteService.buscarDtoPjPorUUID("eA53B7bC-dB84-87D4-2734-545Ed3c079fE"))
                .thenReturn(Optional.of(new PessoaJuridicaDTO()));

        mockMvc.perform(post("/api/clientes/pessoasjuridicas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoaJuridicaRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void deveDarErroAoCriarUmaPessoaFisica() throws Exception {
        PessoaFisicaRequest pessoaFisicaRequest = new PessoaFisicaRequest(
                "",
                "",
                "",
                "",
                ""
        );

        mockMvc.perform(post("/api/clientes/pessoasfisicas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoaFisicaRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveDarErroAoCriarUmaPessoaJuridica() throws Exception {
        PessoaJuridicaRequest pessoaJuridicaRequest = new PessoaJuridicaRequest(
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        mockMvc.perform(post("/api/clientes/pessoasjuridicas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoaJuridicaRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveAlterarDadosDaPessoaFisica() throws Exception {
        PessoaFisicaRequest pessoaFisicaRequest = new PessoaFisicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "6238",
                "57373714912",
                "KFzrOJhyFr",
                "email@email.com"
        );

        String requestJson = objectMapper.writeValueAsString(pessoaFisicaRequest);

        Mockito.when(clienteService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.of(new Cliente()));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/clientes/pessoasfisicas")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Dados alterados com sucesso!"));
    }

    @Test
    void deveAlterarDadosDaPessoaJuridica() throws Exception {
        PessoaJuridicaRequest pessoaJuridicaRequest = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "1234",
                "57373714912",
                "nome",
                "email@email.com"
        );

        String requestJson = objectMapper.writeValueAsString(pessoaJuridicaRequest);

        Mockito.when(clienteService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.of(new Cliente()));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/clientes/pessoasjuridicas")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Dados alterados com sucesso!"));
    }

    @Test
    void deveDarErroAoAlterarDadosDaPessoaFisica() throws Exception {
        PessoaFisicaRequest pessoaFisicaRequest = new PessoaFisicaRequest(
                "",
                "",
                "",
                "",
                ""
        );

        String requestJson = objectMapper.writeValueAsString(pessoaFisicaRequest);

        Mockito.when(clienteService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.of(new Cliente()));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/clientes/pessoasfisicas")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deveDarErroAoAlterarDadosDaPessoaJuridica() throws Exception {
        PessoaJuridicaRequest pessoaJuridicaRequest = new PessoaJuridicaRequest(
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

        String requestJson = objectMapper.writeValueAsString(pessoaJuridicaRequest);

        Mockito.when(clienteService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.of(new Cliente()));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/clientes/pessoasjuridicas")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveAlterarDadosDaPessoaFisicaPorNaoEncontrar() throws Exception {
        PessoaFisicaRequest pessoaFisicaRequest = new PessoaFisicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "6238",
                "57373714912",
                "KFzrOJhyFr",
                "email@email.com"
        );

        String requestJson = objectMapper.writeValueAsString(pessoaFisicaRequest);

        Mockito.when(clienteService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/clientes/pessoasfisicas")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Cliente ainda não cadastrado!"));
    }

    @Test
    void naoDeveAlterarDadosDaPessoaJuridicaPorNaoEncontrar() throws Exception {
        PessoaJuridicaRequest pessoaJuridicaRequest = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "1234",
                "57373714912",
                "nome",
                "email@email.com"
        );

        String requestJson = objectMapper.writeValueAsString(pessoaJuridicaRequest);

        Mockito.when(clienteService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/clientes/pessoasjuridicas")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Cliente ainda não cadastrado!"));
    }

    @Test
    void deveExcluirClienteExistente() throws Exception {
        when(clienteService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.of(new Cliente()));

        doNothing().when(clienteService).excluirCliente("eA53B7bC-dB84-87D4-2734-545Ed3c079fE");

        mockMvc.perform(delete("/api/clientes/{uuid}", "eA53B7bC-dB84-87D4-2734-545Ed3c079fE")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).excluirCliente("eA53B7bC-dB84-87D4-2734-545Ed3c079fE");
        verify(clienteService, times(1)).excluirCliente("eA53B7bC-dB84-87D4-2734-545Ed3c079fE");
    }

    @Test
    void deveNaoEncontrarClienteAoExcluir() throws Exception {
        when(clienteService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/clientes/{uuid}", "eA53B7bC-dB84-87D4-2734-545Ed3c079fE")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não há cliente cadastrado com o código recebido!"));
    }

    @Test
    public void testProximoClienteFilaVazia() throws Exception {
        mockMvc.perform(get("/api/filas/retirada")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
