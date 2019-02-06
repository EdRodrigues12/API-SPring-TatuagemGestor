package com.br.tattoo.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.br.tattoo.api.model.Cliente;
import com.br.tattoo.api.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente pessoaAtualizar(long codigo, Cliente cliente) {
		Cliente clienteSalva = buscarClientePeloCodigo(codigo);
		
		BeanUtils.copyProperties(cliente,  clienteSalva, "codigo");
		return clienteRepository.save(clienteSalva);
		
		
	}
	
	public Cliente buscarClientePeloCodigo(Long codigo) {
		Optional<Cliente> clienteSalva = clienteRepository.findById(codigo);
		if (!clienteSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return clienteSalva.get();
	}


}
