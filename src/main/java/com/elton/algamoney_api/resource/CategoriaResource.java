package com.elton.algamoney_api.resource;

import com.elton.algamoney_api.event.RecursoCriadoEvent;
import com.elton.algamoney_api.model.Categoria;
import com.elton.algamoney_api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias") // mapeamento para o recurso http://localhost:8080/categorias
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Categoria> categoria = categoriaRepository.findById(codigo);
		
		return categoria.isPresent() ? 
				ResponseEntity.ok(categoria.get()) : //Status 200 OK
			    ResponseEntity.noContent().build(); // Status 204 No Content
		
//		if (categoria.isPresent()) {
//	        return ResponseEntity.ok(categoria.get()); // Status 200 OK
//	    } else {
//	        return ResponseEntity.noContent().build(); // Status 204 No Content
//	    }       

	}

}
