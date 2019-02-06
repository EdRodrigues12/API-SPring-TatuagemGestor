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
import com.br.tattoo.api.repository.AgendaMarcadaRepository;
import com.br.tattoo.api.service.AgendaMarcadaService;

@RestController
@RequestMapping("/agendas")
public class AgendaMarcadaResource {
	
	@Autowired
	private AgendaMarcadaRepository agendaRepository;
	
	@Autowired
	private AgendaMarcadaService agendaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<AgendaMarcada> criar(@Valid @RequestBody AgendaMarcada agenda, HttpServletResponse response) {
		AgendaMarcada agendaSalva = agendaRepository.save(agenda);
		
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
//				.buildAndExpand(pessoaSalva.getCodigo()).toUri();
//			response.setHeader("Location", uri.toASCIIString());
//			
//			return ResponseEntity.created(uri).body(pessoaSalva);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, agendaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(agendaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<AgendaMarcada> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<AgendaMarcada> agenda = agendaRepository.findById(codigo);
		 return agenda.get() != null ? ResponseEntity.ok(agenda.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/codigo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		agendaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<AgendaMarcada> atualizar(@PathVariable Long codigo, @Valid @RequestBody AgendaMarcada agenda){
		AgendaMarcada agendaSalva = agendaService.pessoaAtualizar(codigo, agenda);
		return ResponseEntity.ok(agendaSalva);
		
	}
	
	@GetMapping
	public List<AgendaMarcada>listar(){
		
		return agendaRepository.findAll();
	}

}
