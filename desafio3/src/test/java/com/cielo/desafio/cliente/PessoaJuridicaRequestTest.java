package com.cielo.desafio.cliente;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PessoaJuridicaRequestTest {

    private static Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testPessoaJuridicaRequestValido() {
        PessoaJuridicaRequest request = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "1234",
                "57373714912",
                "nome",
                "email@email.com"
        );

        Set<ConstraintViolation<PessoaJuridicaRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    public void testUuidInvalido() {
        PessoaJuridicaRequest request = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE55555555555555",
                "57373714912121",
                "razao",
                "1234",
                "57373714912",
                "nome",
                "email@email.com"
        );

        Set<ConstraintViolation<PessoaJuridicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Não é um formato de identificador válido", violations.iterator().next().getMessage());
    }

    @Test
    public void testCnpjInvalido() {
        PessoaJuridicaRequest request = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121aaaaaaaaaaaaaaa",
                "razao",
                "1234",
                "57373714912",
                "nome",
                "email@email.com"
        );

        Set<ConstraintViolation<PessoaJuridicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("O CNPJ deve ser composto por 14 dígitos", violations.iterator().next().getMessage());
    }

    @Test
    public void testCategoriaComercialInvalida() {
        PessoaJuridicaRequest request = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "12344444444444444444444444444444444444444",
                "57373714912",
                "nome",
                "email@email.com"
        );

        Set<ConstraintViolation<PessoaJuridicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Categoria comercial deve conter no máximo 4 dígitos", violations.iterator().next().getMessage());
    }

    @Test
    public void testCpfInvalido() {
        PessoaJuridicaRequest request = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "1234",
                "5737371491111111111111111111",
                "nome",
                "email@email.com"
        );

        Set<ConstraintViolation<PessoaJuridicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("O CPF deve ser composto por 11 dígitos", violations.iterator().next().getMessage());
    }

    @Test
    public void testNomeInvalido() {
        PessoaJuridicaRequest request = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "1234",
                "57373714912",
                "nome111111111111111111111111111111111111111111111111111111111111111111",
                "email@email.com"
        );
        Set<ConstraintViolation<PessoaJuridicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("O nome deve ser composto só por letras e ter no máximo 50 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailInvalido() {
        PessoaJuridicaRequest request = new PessoaJuridicaRequest(
                "eA53B7bC-dB84-87D4-2734-545Ed3c079fE",
                "57373714912121",
                "razao",
                "1234",
                "57373714912",
                "nome",
                "emailemailcom"
        );

        Set<ConstraintViolation<PessoaJuridicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Não é um formato de e-mail válido", violations.iterator().next().getMessage());
    }

}