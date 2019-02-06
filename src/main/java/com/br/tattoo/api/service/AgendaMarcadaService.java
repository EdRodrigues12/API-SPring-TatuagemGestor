package com.br.tattoo.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.tattoo.api.model.AgendaMarcada;
import com.br.tattoo.api.repository.AgendaMarcadaRepository;

@Service
public class AgendaMarcadaService {
	
	@Autowired
	private AgendaMarcadaRepository agendaRepository;
	
	public AgendaMarcada pessoaAtualizar(long codigo, AgendaMarcada agenda) {
		AgendaMarcada agendaSalva = buscarClientePeloCodigo(codigo);
		
		BeanUtils.copyProperties(agenda,  agendaSalva, "codigo");
		return agendaRepository.save(agendaSalva);
		
		
	}
	
	public AgendaMarcada buscarClientePeloCodigo(Long codigo) {
		Optional<AgendaMarcada> agendaSalva = agendaRepository.findById(codigo);
		if (!agendaSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return agendaSalva.get();
	}

}
