package com.br.tattoo.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.tattoo.api.model.Telefone;
import com.br.tattoo.api.repository.TelefoneRepository;

@Service
public class TelefoneService {
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	public Telefone telefoneAtualizar(long codigo, Telefone telefone) {
		Telefone telefoneSalva = buscarClientePeloCodigo(codigo);
		
		BeanUtils.copyProperties(telefone,  telefoneSalva, "codigo");
		return telefoneRepository.save(telefoneSalva);
		
		
	}
	
	public Telefone buscarClientePeloCodigo(Long codigo) {
		Optional<Telefone> telefoneSalva = telefoneRepository.findById(codigo);
		if (!telefoneSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return telefoneSalva.get();
	}

}
