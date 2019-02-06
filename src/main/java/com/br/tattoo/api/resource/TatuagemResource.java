package com.br.tattoo.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.tattoo.api.event.RecursoCriadoEvent;
import com.br.tattoo.api.model.Tatuagem;
import com.br.tattoo.api.repository.TatuagemRepository;
import com.br.tattoo.api.service.TatuagemService;

@RestController
@RequestMapping("/tatuagens")
public class TatuagemResource {
	
	@Autowired
	private TatuagemRepository tatuagemRepository;
	@Autowired
	private TatuagemService tatuagemService;	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<Tatuagem> criar(@Valid @RequestBody Tatuagem tatuagem, HttpServletResponse response) {
		
		Tatuagem tatuagemSalva = tatuagemRepository.save(tatuagem);
		
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
//				.buildAndExpand(pessoaSalva.getCodigo()).toUri();
//			response.setHeader("Location", uri.toASCIIString());
//			
//			return ResponseEntity.created(uri).body(pessoaSalva);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, tatuagemSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(tatuagemSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Tatuagem> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Tatuagem> tatuagem = tatuagemRepository.findById(codigo);
		 return tatuagem.get() != null ? ResponseEntity.ok(tatuagem.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/codigo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		tatuagemRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Tatuagem> atualizar(@PathVariable Long codigo, @Valid @RequestBody Tatuagem tatuagem){
		Tatuagem tatuagemSalva = tatuagemService.tatuagemAtualizar(codigo, tatuagem);
		return ResponseEntity.ok(tatuagemSalva);
		
	}
	
	@GetMapping("/tatuagenslist")
	public List<Tatuagem>listTatuagens(){
		return tatuagemRepository.findAll();
	}

}
