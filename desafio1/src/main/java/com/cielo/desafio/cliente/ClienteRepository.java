package com.cielo.desafio.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByUuid(String uuid);
    Optional<Cliente> findByTipoCliente(TipoCliente tipoCliente);

}