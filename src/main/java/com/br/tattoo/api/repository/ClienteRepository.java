package com.br.tattoo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.tattoo.api.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
