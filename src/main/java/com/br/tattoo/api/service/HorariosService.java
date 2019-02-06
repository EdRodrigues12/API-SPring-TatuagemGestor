package com.br.tattoo.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.tattoo.api.model.Horarios;
import com.br.tattoo.api.repository.HorariosRepository;

@Service
public class HorariosService {
	
	@Autowired
	private HorariosRepository horariosRepository;
	
	public List<Horarios> listHorarios(){
		
		return horariosRepository.findAll();
		
	}

}
