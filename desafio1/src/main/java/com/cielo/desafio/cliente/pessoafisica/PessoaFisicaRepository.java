package com.cielo.desafio.cliente.pessoafisica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    Optional<PessoaFisica> findByUuid(UUID uuid);

}
