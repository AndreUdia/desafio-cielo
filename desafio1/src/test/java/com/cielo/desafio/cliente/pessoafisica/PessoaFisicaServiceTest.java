package com.cielo.desafio.cliente.pessoafisica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
class PessoaFisicaServiceTest {

    @Mock
    private PessoaFisicaRepository repository;

    @InjectMocks
    private PessoaFisicaService service;

    private PessoaFisica pessoaFisica;

    @BeforeEach
    public void setUp() {
        pessoaFisica = new PessoaFisica();
        pessoaFisica.setId(1L);
        pessoaFisica.setUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE");
        pessoaFisica.setCpf("12345678900");
        pessoaFisica.setNome("Nome");
        pessoaFisica.setEmail("email@email.com");
        pessoaFisica.setCategoriaComercial("1234");
    }

    @Test
    void testListarTodas() {
        List<PessoaFisica> pessoaFisicaList = new ArrayList<>();
        pessoaFisicaList.add(pessoaFisica);

        when(repository.findAll()).thenReturn(pessoaFisicaList);

        List<PessoaFisicaDTO> result = service.listarTodas();

        assertEquals(pessoaFisicaList.size(), result.size());
    }

    @Test
    void testSalvarPessoaFisica() {
        service.salvarPessoaFisica(pessoaFisica);
        verify(repository).save(pessoaFisica);
    }

    @Test
    void testExcluirPessoaFisica() {
        String uuid = "eA53B7bC-dB84-87D4-2734-545Ed3c079fE";

        when(repository.findByUuid(uuid)).thenReturn(Optional.of(pessoaFisica));

        service.excluirPessoaFisica(uuid);

        verify(repository).deleteById(pessoaFisica.getId());
    }

    @Test
    void testFindByUuid() {
        String uuid = "eA53B7bC-dB84-87D4-2734-545Ed3c079fE";

        when(repository.findByUuid(uuid)).thenReturn(Optional.of(pessoaFisica));

        Optional<PessoaFisica> resultado = service.findByUuid(uuid);

        assertTrue(resultado.isPresent());
        assertEquals(Optional.of(pessoaFisica), resultado);
    }

    @Test
    void testFindByUuidComUuidInvalido() {
        String uuid = "eA53B545Ed3c079fE";

        Optional<PessoaFisica> resultado = service.findByUuid(uuid);

        assertFalse(resultado.isPresent());
        assertEquals(Optional.empty(), resultado);
        verify(repository).findByUuid(uuid);
    }
}