package com.cielo.desafio.utils;

import com.cielo.desafio.cliente.Cliente;
import com.cielo.desafio.cliente.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilaClienteTest {

    FilaCliente filaClienteTest;

    @BeforeEach
    public void setUp() {
        filaClienteTest = new FilaCliente(2);
    }

    @Test
    public void testEnfileirarEDesenfileirar() {
        Cliente cliente1 = new Cliente(1L, "75f95e85-8669-46d9-b91c-e27672605959","120123123123123","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);
        Cliente cliente2 = new Cliente(2L, "75f95e85-8669-46d9-b91c-e27672605960","120123123123124","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);
        Cliente cliente3 = new Cliente(3L, "75f95e85-8669-46d9-b91c-e27672605961","120123123123125","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);

        filaClienteTest.enfileirar(cliente1);
        filaClienteTest.enfileirar(cliente2);

        assertEquals(cliente1, filaClienteTest.desenfileirar());
        assertEquals(cliente2, filaClienteTest.desenfileirar());
    }

    @Test
    public void testTamanho() {
        Cliente cliente1 = new Cliente(1L, "75f95e85-8669-46d9-b91c-e27672605959","120123123123123","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);
        Cliente cliente2 = new Cliente(2L, "75f95e85-8669-46d9-b91c-e27672605960","120123123123124","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);

        filaClienteTest.enfileirar(cliente1);
        filaClienteTest.enfileirar(cliente2);

        assertEquals(2, filaClienteTest.getTamanho());
    }

    @Test
    public void testRedimensionar() {
        Cliente cliente1 = new Cliente(1L, "75f95e85-8669-46d9-b91c-e27672605959","120123123123123","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);
        Cliente cliente2 = new Cliente(2L, "75f95e85-8669-46d9-b91c-e27672605960","120123123123124","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);

        filaClienteTest.enfileirar(cliente1);
        filaClienteTest.enfileirar(cliente2);

        Cliente cliente3 = new Cliente(3L, "75f95e85-8669-46d9-b91c-e27672605961","120123123123125","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);

        filaClienteTest.enfileirar(cliente3);

        assertEquals(4, filaClienteTest.getCapacidade());
    }

    @Test
    public void testEstaVazia() {
        assertTrue(filaClienteTest.estaVazia());

        Cliente cliente1 = new Cliente(1L, "75f95e85-8669-46d9-b91c-e27672605961","120123123123125","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);

        filaClienteTest.enfileirar(cliente1);

        assertFalse(filaClienteTest.estaVazia());
    }

    @Test
    public void testTamanho_AfterEnqueueAndDequeue() {
        Cliente cliente1 = new Cliente(1L, "75f95e85-8669-46d9-b91c-e27672605959","120123123123123","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);
        Cliente cliente2 = new Cliente(2L, "75f95e85-8669-46d9-b91c-e27672605960","120123123123124","razsoci",
                "nome", "email", "cpfContato", "12313213211", TipoCliente.PJ);

        filaClienteTest.enfileirar(cliente1);
        filaClienteTest.enfileirar(cliente2);

        filaClienteTest.desenfileirar();

        assertEquals(1, filaClienteTest.getTamanho());
    }

}