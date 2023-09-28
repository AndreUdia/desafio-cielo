package com.cielo.desafio.cliente.pessoajuridica;

import com.cielo.desafio.cliente.Cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaJuridica extends Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_juridica_seq")
    @SequenceGenerator(name = "pessoa_juridica_seq", sequenceName = "PESSOA_JURIDICA_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cnpj;

    private String razaoSocial;

    private String cpfContato;
}
