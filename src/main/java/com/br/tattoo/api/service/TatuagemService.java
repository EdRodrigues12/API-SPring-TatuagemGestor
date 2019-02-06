package com.br.tattoo.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.tattoo.api.model.Tatuagem;
import com.br.tattoo.api.repository.TatuagemRepository;

@Service
public class TatuagemService {
	
	@Autowired
	private TatuagemRepository tatuagemRepository;

	public Tatuagem tatuagemAtualizar(long codigo, Tatuagem tatuagem) {
		Tatuagem tatuagemSalva = buscarTatuagemPeloCodigo(codigo);
		
		BeanUtils.copyProperties(tatuagem,  tatuagemSalva, "codigo");
		return tatuagemRepository.save(tatuagemSalva);
		
		
	}
	
	public Tatuagem buscarTatuagemPeloCodigo(Long codigo) {
		Optional<Tatuagem> tatuagemSalva = tatuagemRepository.findById(codigo);
		if (!tatuagemSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return tatuagemSalva.get();
	}
}
