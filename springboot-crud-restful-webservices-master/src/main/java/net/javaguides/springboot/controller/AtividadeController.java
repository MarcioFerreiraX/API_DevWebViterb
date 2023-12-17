package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.Atividade;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.repository.AtividadeRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

	@Autowired
	private AtividadeRepository atividadeRepository;

	@Autowired
	private UserRepository userRepository;

	// Listar todas as atividades
	@GetMapping
	public List<Atividade> getAllAtividades() {
		return atividadeRepository.findAll();
	}

	// Buscar atividade por ID
	@GetMapping("/{id}")
	public ResponseEntity<Atividade> getAtividadeById(@PathVariable(value = "id") Long atividadeId) throws ResourceNotFoundException {
		Atividade atividade = atividadeRepository.findById(atividadeId)
				.orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada com o ID: " + atividadeId));
		return ResponseEntity.ok().body(atividade);
	}

	// Criar uma nova atividade
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
	public Atividade createAtividade(@Valid @RequestBody Atividade atividade) {
		return atividadeRepository.save(atividade);
	}

	// Atualizar uma atividade
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
	public ResponseEntity<Atividade> updateAtividade(@PathVariable(value = "id") Long atividadeId,
													 @Valid @RequestBody Atividade detalhesAtividade) throws ResourceNotFoundException {
		Atividade atividade = atividadeRepository.findById(atividadeId)
				.orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada com o ID: " + atividadeId));

		atividade.setNome(detalhesAtividade.getNome());
		atividade.setTipo(detalhesAtividade.getTipo());
		atividade.setDescricao(detalhesAtividade.getDescricao());
		atividade.setData(detalhesAtividade.getData());
		atividade.setHorarioInicial(detalhesAtividade.getHorarioInicial());
		atividade.setHorarioFinal(detalhesAtividade.getHorarioFinal());
		atividade.setEspaco(detalhesAtividade.getEspaco());
		final Atividade updatedAtividade = atividadeRepository.save(atividade);
		return ResponseEntity.ok(updatedAtividade);
	}

	// Excluir uma atividade
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
	public ResponseEntity<?> deleteAtividade(@PathVariable(value = "id") Long atividadeId) throws ResourceNotFoundException {
		Atividade atividade = atividadeRepository.findById(atividadeId)
				.orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada com o ID: " + atividadeId));

		atividadeRepository.delete(atividade);
		return ResponseEntity.ok().build();
	}

	// Favoritar uma atividade
	@PostMapping("/{atividadeId}/favoritar")
	public ResponseEntity<?> favoritarAtividade(@PathVariable Long atividadeId, Authentication authentication) {
		User user = userRepository.findByLogin(authentication.getName())
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

		Atividade atividade = atividadeRepository.findById(atividadeId)
				.orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada"));

		user.addAtividadeFavorita(atividade);
		userRepository.save(user);
		return ResponseEntity.ok().build();
	}

	// Desfavoritar uma atividade
	@DeleteMapping("/{atividadeId}/desfavoritar")
	public ResponseEntity<?> desfavoritarAtividade(@PathVariable Long atividadeId, Authentication authentication) {
		User user = userRepository.findByLogin(authentication.getName())
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

		Atividade atividade = atividadeRepository.findById(atividadeId)
				.orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada"));

		user.removeAtividadeFavorita(atividade);
		userRepository.save(user);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/favoritas/{userId}")
	public ResponseEntity<List<Atividade>> getAtividadesFavoritas(@PathVariable(value = "userId") Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));

		List<Atividade> atividadesFavoritas = new ArrayList<>(user.getAtividadesFavoritas());
		return ResponseEntity.ok(atividadesFavoritas);
	}
}
