package com.cielo.desafio.cliente.pessoafisica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PessoaFisicaTest {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    private PessoaFisica pessoaFisicaUm;

    @BeforeEach
    public void setUp() {
        pessoaFisicaUm = new PessoaFisica();
        pessoaFisicaUm.setCpf("12345678900");
    }

    @Test
    public void testIdGetterAndSetter() {
        pessoaFisicaUm.setId(1L);
        assertEquals(1L, pessoaFisicaUm.getId());
    }

    @Test
    public void testGetterAndSetterDeUmCpfValido() {
        assertEquals("12345678900", pessoaFisicaUm.getCpf());
    }

    @Test
    public void testPessoaFisicaInseridaNoBancoDe() {
        pessoaFisicaRepository.save(pessoaFisicaUm);

        PessoaFisica pessoaFisicaSalva = pessoaFisicaRepository.findById(pessoaFisicaUm.getId()).orElse(null);
        assertNotNull(pessoaFisicaSalva);
        assertEquals("12345678900", pessoaFisicaSalva.getCpf());
    }

    @Test
    public void testPessoaFisicaSalvaNoBancoDeDados() {
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisicaRepository.save(pessoaFisica);

        assertNotNull(pessoaFisica.getId());
    }
}
