package com.cielo.desafio.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Mock
    private ModelMapper modelMapper;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setUuid("eA53B7bC-dB84-87D4-2734-545Ed3c079fE");
        cliente.setCadastroNacional("12345678900");
        cliente.setNome("Nome");
        cliente.setEmail("email@email.com");
        cliente.setCategoriaComercial("1234");
    }

    @Test
    void testListarTodas() {
        List<Cliente> pessoaFisicaList = new ArrayList<>();
        pessoaFisicaList.add(cliente);

        when(repository.findByTipoCliente(TipoCliente.PF)).thenReturn(Optional.of(cliente));

        List<Cliente> result = service.listarTodas(TipoCliente.PF);

        assertEquals(pessoaFisicaList.size(), result.size());
    }

    @Test
    void testSalvarCliente() {
        service.salvarCliente(cliente, TipoCliente.PF);
        verify(repository).save(cliente);
    }

    @Test
    void testExcluirPessoaFisica() {
        String uuid = "eA53B7bC-dB84-87D4-2734-545Ed3c079fE";

        when(repository.findByUuid(uuid)).thenReturn(Optional.of(cliente));

        service.excluirCliente("eA53B7bC-dB84-87D4-2734-545Ed3c079fE");

        verify(repository).deleteById(cliente.getId());
    }

    @Test
    void testFindByUuid() {
        String uuid = "eA53B7bC-dB84-87D4-2734-545Ed3c079fE";

        when(repository.findByUuid(uuid)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = service.findByUuid(uuid);

        assertTrue(resultado.isPresent());
        assertEquals(Optional.of(cliente), resultado);
    }

    @Test
    void testFindByUuidComUuidInvalido() {
        String uuid = "eA53B545Ed3c079fE";

        Optional<Cliente> resultado = service.findByUuid(uuid);

        assertFalse(resultado.isPresent());
        assertEquals(Optional.empty(), resultado);
        verify(repository).findByUuid(uuid);
    }
}