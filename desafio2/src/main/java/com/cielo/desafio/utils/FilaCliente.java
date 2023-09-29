package com.cielo.desafio.utils;

import com.cielo.desafio.cliente.Cliente;
import lombok.Getter;

public class FilaCliente {
    private Cliente[] clientes;

    @Getter
    private int tamanho;

    @Getter
    private int capacidade;
    private int inicio;

    public FilaCliente(int capacidade){
        this.clientes = new Cliente[capacidade];
        this.tamanho = 0;
        this.capacidade = capacidade;
        this.inicio = 0;
    }

    public void enfileirar(Cliente cliente) {
        if (tamanho < capacidade) {
            int indice = (inicio + tamanho) % capacidade;
            clientes[indice] = cliente;
            tamanho++;
        } else {
            redimensionar();
            int indice = (inicio + tamanho) % capacidade;
            clientes[indice] = cliente;
            tamanho++;
        }
    }

    public Cliente desenfileirar() {
        if (!estaVazia()) {
            Cliente clienteRemovido = clientes[inicio];
            clientes[inicio] = null;
            inicio = (inicio + 1) % capacidade;
            tamanho--;
            return clienteRemovido;
        } else {
            throw new IllegalStateException("Fila está vazia.");
        }
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    private void redimensionar() {
        int novaCapacidade = capacidade * 2;
        Cliente[] novoArray = new Cliente[novaCapacidade];

        for (int i = 0; i < tamanho; i++) {
            int indice = (inicio + i) % capacidade;
            novoArray[i] = clientes[indice];
        }

        clientes = novoArray;
        capacidade = novaCapacidade;
        inicio = 0;
    }

}
