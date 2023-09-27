package com.cielo.desafio.cliente.pessoafisica;

import com.cielo.desafio.cliente.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.intellij.lang.annotations.Pattern;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaFisica extends Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_fisica_seq")
    @SequenceGenerator(name = "pessoa_fisica_seq", sequenceName = "PESSOA_FISICA_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    public PessoaFisica(UUID uuid, String categoriaComercial, String cpf, String nome, String email) {
        super();
    }

}
