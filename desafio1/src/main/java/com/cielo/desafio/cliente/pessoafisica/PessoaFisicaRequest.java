package com.cielo.desafio.cliente.pessoafisica;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFisicaRequest {

    @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}",
            message = "Não é um formato de identificador válido")
    private String uuid;

    @Pattern(regexp = "[0-9]{1,4}",
            message = "Categoria comercial deve conter no máximo 4 dígitos")
    private String categoriaComercial;

    @Pattern(regexp = "[0-9]{11}", message = "O CPF deve ser composto por 11 dígitos")
    private String cpf;

    @Pattern(regexp = "[a-zA-Z]{1,50}",
            message = "O nome deve ser composto só por letras e ter no máximo 50 caracteres")
    private String nome;

    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$",
            message = "Não é um formato de e-mail válido")
    private String email;

}
