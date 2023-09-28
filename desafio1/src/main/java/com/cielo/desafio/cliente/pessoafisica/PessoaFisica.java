package com.cielo.desafio.cliente.pessoafisica;

import com.cielo.desafio.cliente.Cliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PessoaFisica extends Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_fisica_seq")
    @SequenceGenerator(name = "pessoa_fisica_seq", sequenceName = "PESSOA_FISICA_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cpf;
}
