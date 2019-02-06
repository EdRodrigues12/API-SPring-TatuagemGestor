package com.br.tattoo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.tattoo.api.model.Tatuagem;

public interface TatuagemRepository extends JpaRepository<Tatuagem, Long> {

}
