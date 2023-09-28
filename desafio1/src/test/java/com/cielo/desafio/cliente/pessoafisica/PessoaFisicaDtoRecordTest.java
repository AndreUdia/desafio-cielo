package com.cielo.desafio.cliente.pessoafisica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PessoaFisicaDtoRecordTest {

    @Test
    public void testPessoaFisicaDtoRecordConstructor() {
        PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO("fc0a4d58-0399-4b19-bdea-43d7042b9c7b", "1111", "12345678900", "nome", "email@email");
        PessoaFisicaDtoRecord record = new PessoaFisicaDtoRecord(pessoaFisicaDTO);

        assertEquals("fc0a4d58-0399-4b19-bdea-43d7042b9c7b", record.uuid());
        assertEquals("1111", record.categoriaComercial());
        assertEquals("12345678900", record.cpf());
        assertEquals("nome", record.nome());
        assertEquals("email@email", record.email());
    }
}