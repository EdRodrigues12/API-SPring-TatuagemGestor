package com.br.tattoo.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.tattoo.api.event.RecursoCriadoEvent;
import com.br.tattoo.api.model.AgendaMarcada;
import com.br.tattoo.api.model.Cliente;
import com.br.tattoo.api.model.Horarios;
import com.br.tattoo.api.repository.AgendaMarcadaRepository;
import com.br.tattoo.api.repository.ClienteRepository;
import com.br.tattoo.api.service.ClienteService;
import com.br.tattoo.api.service.HorariosService;


@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private AgendaMarcadaRepository agendaMarcadaRepository;
	
	@Autowired
	private HorariosService horarioService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	private AgendaMarcada agendaMarcadaSalva;
	
	@PostMapping
	public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
	
		cliente.setAgendaMarcada(agendaMarcadaSalva);
		Cliente clienteSalva = clienteRepository.save(cliente);
		
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
//				.buildAndExpand(pessoaSalva.getCodigo()).toUri();
//			response.setHeader("Location", uri.toASCIIString());
//			
//			return ResponseEntity.created(uri).body(pessoaSalva);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalva);
	}
	
	@PostMapping("/agenda")
	public ResponseEntity<AgendaMarcada> criarAgendaMarcada( @Valid @RequestBody AgendaMarcada agenda, HttpServletResponse response) {
		 agendaMarcadaSalva = agendaMarcadaRepository.save(agenda);
		
		
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
//				.buildAndExpand(pessoaSalva.getCodigo()).toUri();
//			response.setHeader("Location", uri.toASCIIString());
//			
//			return ResponseEntity.created(uri).body(pessoaSalva);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, agendaMarcadaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(agendaMarcadaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Cliente> cliente = clienteRepository.findById(codigo);
		 return cliente.get() != null ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		clienteRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long codigo, @Valid @RequestBody Cliente pessoa){
		Cliente clienteSalva = clienteService.pessoaAtualizar(codigo, pessoa);
		return ResponseEntity.ok(clienteSalva);
		
	}
	
	@GetMapping("/horarios")
	public List<Horarios>listHorarios(){
		return horarioService.listHorarios();
	}

}
