package com.cielo.desafio.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ClienteRequestFisicaTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente clienteUm;

    @BeforeEach
    public void setUp() {
        clienteUm = new Cliente();
        clienteUm.setCadastroNacional("12345678900");
    }

    @Test
    public void testIdGetterAndSetter() {
        clienteUm.setId(1L);
        assertEquals(1L, clienteUm.getId());
    }

    @Test
    public void testGetterAndSetterDeUmCpfValido() {
        assertEquals("12345678900", clienteUm.getCadastroNacional());
    }

    @Test
    public void testPessoaFisicaInseridaNoBancoDe() {
        clienteRepository.save(clienteUm);

        Cliente clienteSalvo = clienteRepository.findById(clienteUm.getId()).orElse(null);
        assertNotNull(clienteSalvo);
        assertEquals("12345678900", clienteSalvo.getCadastroNacional());
    }

    @Test
    public void testPessoaFisicaSalvaNoBancoDeDados() {
        Cliente cliente = new Cliente();
        clienteRepository.save(cliente);

        assertNotNull(cliente.getId());
    }
}
