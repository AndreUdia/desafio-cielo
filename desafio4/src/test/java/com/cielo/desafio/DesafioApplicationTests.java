package com.cielo.desafio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = {"com.cielo.desafio.cliente.pessoajuridica"})
class DesafioApplicationTests {

    @Test
    void contextLoads() {
    }

}
