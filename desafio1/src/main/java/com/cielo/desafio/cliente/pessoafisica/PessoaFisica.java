package com.cielo.desafio.cliente.pessoafisica;

import com.cielo.desafio.cliente.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.intellij.lang.annotations.Pattern;

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

    @Pattern("\"^[0-9]{11}$\"")
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
}
