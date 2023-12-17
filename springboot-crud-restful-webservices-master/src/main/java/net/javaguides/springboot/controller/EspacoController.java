package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.Espaco;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.repository.EspacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/espacos")
public class EspacoController {

	@Autowired
	private EspacoRepository espacoRepository;

	// Listar todos os espaços
	@GetMapping
	public List<Espaco> getAllEspacos() {
		return espacoRepository.findAll();
	}

	// Buscar espaço por ID
	@GetMapping("/{id}")
	public ResponseEntity<Espaco> getEspacoById(@PathVariable(value = "id") Long espacoId) throws ResourceNotFoundException {
		Espaco espaco = espacoRepository.findById(espacoId)
				.orElseThrow(() -> new ResourceNotFoundException("Espaço não encontrado com o ID: " + espacoId));
		return ResponseEntity.ok().body(espaco);
	}

	// Criar um novo espaço
	@PostMapping
	@PreAuthorize("hasAnyRole('ORGANIZADOR')")
	public Espaco createEspaco(@Valid @RequestBody Espaco espaco) {
		return espacoRepository.save(espaco);
	}

	// Atualizar um espaço
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ORGANIZADOR')")
	public ResponseEntity<Espaco> updateEspaco(@PathVariable(value = "id") Long espacoId,
											   @Valid @RequestBody Espaco detalhesEspaco) throws ResourceNotFoundException {
		Espaco existingEspaco = espacoRepository.findById(espacoId)
				.orElseThrow(() -> new ResourceNotFoundException("Espaço não encontrado com o ID: " + espacoId));

		existingEspaco.setNome(detalhesEspaco.getNome());
		existingEspaco.setLocalizacao(detalhesEspaco.getLocalizacao());
		existingEspaco.setCapacidade(detalhesEspaco.getCapacidade());
		existingEspaco.setRecursos(detalhesEspaco.getRecursos());
		final Espaco updatedEspaco = espacoRepository.save(existingEspaco);
		return ResponseEntity.ok(updatedEspaco);
	}

	// Excluir um espaço
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ORGANIZADOR')")
	public ResponseEntity<?> deleteEspaco(@PathVariable(value = "id") Long espacoId) throws ResourceNotFoundException {
		Espaco espaco = espacoRepository.findById(espacoId)
				.orElseThrow(() -> new ResourceNotFoundException("Espaço não encontrado com o ID: " + espacoId));

		espacoRepository.delete(espaco);
		return ResponseEntity.ok().build();
	}
}
