package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.Evento;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

	@Autowired
	private EventoRepository eventoRepository;

	// Listar todos os eventos
	@GetMapping
	public List<Evento> getAllEventos() {
		return eventoRepository.findAll();
	}

	// Buscar evento por ID
	@GetMapping("/{id}")
	public ResponseEntity<Evento> getEventoById(@PathVariable(value = "id") Long eventoId) throws ResourceNotFoundException {
		Evento evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + eventoId));
		return ResponseEntity.ok().body(evento);
	}

	// Criar um novo evento
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Evento createEvento(@Valid @RequestBody Evento evento) {
		return eventoRepository.save(evento);
	}

	// Atualizar um evento
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Evento> updateEvento(@PathVariable(value = "id") Long eventoId,
											   @Valid @RequestBody Evento detalhesEvento) throws ResourceNotFoundException {
		Evento existingEvento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + eventoId));

		existingEvento.setNome(detalhesEvento.getNome());
		existingEvento.setSigla(detalhesEvento.getSigla());
		existingEvento.setDescricao(detalhesEvento.getDescricao());
		existingEvento.setCaminho(detalhesEvento.getCaminho());
		final Evento updatedEvento = eventoRepository.save(existingEvento);
		return ResponseEntity.ok(updatedEvento);
	}

	// Excluir um evento
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteEvento(@PathVariable(value = "id") Long eventoId) throws ResourceNotFoundException {
		Evento evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + eventoId));

		eventoRepository.delete(evento);
		return ResponseEntity.ok().build();
	}
}
