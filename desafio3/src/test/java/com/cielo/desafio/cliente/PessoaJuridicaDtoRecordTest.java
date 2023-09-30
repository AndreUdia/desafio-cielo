package com.cielo.desafio.cliente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PessoaJuridicaDtoRecordTest {

    @Test
    public void testPessoaFisicaDtoRecordConstructor() {
        PessoaJuridicaDTO pessoa = new PessoaJuridicaDTO(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "1234",
                "57373714912",
                "nome",
                "email@email.com"
        );
        PessoaJuridicaDtoRecord record = new PessoaJuridicaDtoRecord(pessoa);

        assertEquals("eA53B7bC-dB84-87D4-2734-545Ed3c079fE", record.uuid());
        assertEquals("57373714912121", record.cadastroNacional());
        assertEquals("razao", record.razaoSocial());
        assertEquals("1234", record.categoriaComercial());
        assertEquals("57373714912", record.cpfContato());
        assertEquals("nome", record.nome());
        assertEquals("email@email.com", record.email());
    }
}
