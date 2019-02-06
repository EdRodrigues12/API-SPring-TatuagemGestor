package com.br.tattoo.api.resource;

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
import com.br.tattoo.api.model.Telefone;
import com.br.tattoo.api.repository.TelefoneRepository;
import com.br.tattoo.api.service.TelefoneService;

@RestController
@RequestMapping("/telefones")
public class TelefoneResource {
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	@Autowired
	private TelefoneService telefoneService;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<Telefone> criar(@Valid @RequestBody Telefone telefone, HttpServletResponse response) {
		Telefone telefoneSalva = telefoneRepository.save(telefone);
		
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
//				.buildAndExpand(pessoaSalva.getCodigo()).toUri();
//			response.setHeader("Location", uri.toASCIIString());
//			
//			return ResponseEntity.created(uri).body(pessoaSalva);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, telefoneSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(telefoneSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Telefone> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Telefone> telefone = telefoneRepository.findById(codigo);
		 return telefone.get() != null ? ResponseEntity.ok(telefone.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/codigo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		telefoneRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Telefone> atualizar(@PathVariable Long codigo, @Valid @RequestBody Telefone telefone){
		Telefone telefoneSalva = telefoneService.telefoneAtualizar(codigo, telefone);
		return ResponseEntity.ok(telefoneSalva);
		
	}
}
