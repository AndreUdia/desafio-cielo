package com.cielo.desafio.user;

public enum Roles {
    ADM("Administrador"),
    REST("UsuarioRestrito");

    private final String val;

    private Roles(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}
