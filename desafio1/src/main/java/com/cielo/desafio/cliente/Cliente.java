package com.cielo.desafio.cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "CLIENTE_SEQ", allocationSize = 1)
    private Long id;

    private String uuid;

    @Column(nullable = false, unique = true)
    private String cadastroNacional;

    private String razaoSocial;

    private String categoriaComercial;

    private String nome;

    private String email;

    private String cpfContato;

    private TipoCliente tipoCliente;
}
