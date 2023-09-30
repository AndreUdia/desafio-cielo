package com.cielo.desafio.cliente;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private String uuid;
    private String cadastroNacional;
    private String categoriaComercial;
    private String nome;
    private String email;
}
