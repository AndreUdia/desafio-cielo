package com.cielo.desafio.cliente;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PessoaFisicaRequestTest {

    private static Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testPessoaFisicaRequestValido() {
        PessoaFisicaRequest request = new PessoaFisicaRequest(
                "fc0a4d58-0399-4b19-bdea-43d7042b9c7b",
                "1234",
                "12345678901",
                "Nome",
                "email.email@email.com"
        );

        Set<ConstraintViolation<PessoaFisicaRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    public void testUuidInvalido() {
        PessoaFisicaRequest request = new PessoaFisicaRequest(
                "fc0a4d58-0399-4b19-bdea-43d7042b9XXX",
                "1234",
                "12345678901",
                "Nome",
                "email.email@email.com"
        );

        Set<ConstraintViolation<PessoaFisicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Não é um formato de identificador válido", violations.iterator().next().getMessage());
    }

    @Test
    public void testCategoriaComercialInvalida() {
        PessoaFisicaRequest request = new PessoaFisicaRequest(
                "fc0a4d58-0399-4b19-bdea-43d7042b9c7b",
                "12345",
                "12345678901",
                "Nome",
                "email.email@email.com"
        );

        Set<ConstraintViolation<PessoaFisicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Categoria comercial deve conter no máximo 4 dígitos", violations.iterator().next().getMessage());
    }

    @Test
    public void testCpfInvalido() {
        PessoaFisicaRequest request = new PessoaFisicaRequest(
                "fc0a4d58-0399-4b19-bdea-43d7042b9c7b",
                "1234",
                "1234",
                "nome",
                "email.email@email.com"
        );

        Set<ConstraintViolation<PessoaFisicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("O CPF deve ser composto por 11 dígitos", violations.iterator().next().getMessage());
    }

    @Test
    public void testNomeInvalido() {
        PessoaFisicaRequest request = new PessoaFisicaRequest(
                "fc0a4d58-0399-4b19-bdea-43d7042b9c7b",
                "1234",
                "12345678901",
                "123456789012345678901234567890123456789012345678909456456644546",
                "email.email@email.com"
        );

        Set<ConstraintViolation<PessoaFisicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("O nome deve ser composto só por letras e ter no máximo 50 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmailInvalido() {
        PessoaFisicaRequest request = new PessoaFisicaRequest(
                "fc0a4d58-0399-4b19-bdea-43d7042b9c7b",
                "1234",
                "12345678901",
                "Nome",
                "email.email@.com"
        );

        Set<ConstraintViolation<PessoaFisicaRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Não é um formato de e-mail válido", violations.iterator().next().getMessage());
    }

}