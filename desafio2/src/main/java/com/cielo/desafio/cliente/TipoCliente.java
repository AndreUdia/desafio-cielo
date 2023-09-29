package com.cielo.desafio.cliente;

public enum TipoCliente {
    PF("pessoaFisica"),  PJ("pessoaJuridica");

    private final String val;

    private TipoCliente(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}
