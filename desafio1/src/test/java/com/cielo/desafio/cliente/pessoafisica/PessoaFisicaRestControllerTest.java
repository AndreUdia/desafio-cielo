package com.cielo.desafio.cliente.pessoafisica;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class PessoaFisicaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaFisicaService pessoaFisicaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarTodasAsPessoasFisicas() throws Exception {
        Mockito.when(this.pessoaFisicaService.listarTodas())
                .thenReturn(new ArrayList<>(){});

        mockMvc.perform(get("/api/clientes/pessoasfisicas"))
                    .andDo(print())
                    .andExpect(status().isOk());
    }

    @Test
    void deveValidarQuandoEncontraUmaPessoaFisicaPeloUUID() throws Exception {
        Mockito.when(pessoaFisicaService.buscarDtoPorUUID("eA53B7bC-dB84-87D4-2734-545Ed3c079fE"))
                .thenReturn(Optional.of(new PessoaFisicaDTO()));

        mockMvc.perform(get("/api/clientes/pessoasfisicas/{uuid}",
                        "eA53B7bC-dB84-87D4-2734-545Ed3c079fE")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void deveValidarQuandoNaoEncontraUmaPessoaFisicaPeloUUID() throws Exception {
        Mockito.when(pessoaFisicaService.buscarDtoPorUUID("eA53B7bC-dB84-87D4-2734-545Ed3c079fE"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/pessoasfisicas/{uuid}",
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
    void deveDarConflitoAoCriarUmaPessoaFisica() throws Exception {
        PessoaFisicaRequest pessoaFisicaRequest = new PessoaFisicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "6238",
                "57373714912",
                "KFzrOJhyFr",
                "email@email.com"
        );

        Mockito.when(this.pessoaFisicaService.buscarDtoPorUUID("eA53B7bC-dB84-87D4-2734-545Ed3c079fE"))
                .thenReturn(Optional.of(new PessoaFisicaDTO()));

        mockMvc.perform(post("/api/clientes/pessoasfisicas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pessoaFisicaRequest)))
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
    void deveAlterarDadosDaPessoaFisica() throws Exception {
        PessoaFisicaRequest pessoaFisicaRequest = new PessoaFisicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "6238",
                "57373714912",
                "KFzrOJhyFr",
                "email@email.com"
        );

        String requestJson = objectMapper.writeValueAsString(pessoaFisicaRequest);

        Mockito.when(pessoaFisicaService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.of(new PessoaFisica()));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/clientes/pessoasfisicas")
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

        Mockito.when(pessoaFisicaService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.of(new PessoaFisica()));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/clientes/pessoasfisicas")
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

        Mockito.when(pessoaFisicaService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/clientes/pessoasfisicas")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Cliente ainda não cadastrado!"));
    }

    @Test
    void deveExcluirPessoaFisicaExistente() throws Exception {
        when(pessoaFisicaService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.of(new PessoaFisica()));

        doNothing().when(pessoaFisicaService).excluirPessoaFisica("eA53B7bC-dB84-87D4-2734-545Ed3c079fE");

        mockMvc.perform(delete("/api/clientes/pessoasfisicas/{uuid}", "eA53B7bC-dB84-87D4-2734-545Ed3c079fE")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(pessoaFisicaService, times(1)).excluirPessoaFisica("eA53B7bC-dB84-87D4-2734-545Ed3c079fE");
        verify(pessoaFisicaService, times(1)).excluirPessoaFisica("eA53B7bC-dB84-87D4-2734-545Ed3c079fE");
    }

    @Test
    void deveNaoEncontrarPessoaFisicaAoExcluir() throws Exception {
        when(pessoaFisicaService.findByUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/clientes/pessoasfisicas/{uuid}", "eA53B7bC-dB84-87D4-2734-545Ed3c079fE")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Não há cliente cadastrado com o código recebido!"));
    }

}