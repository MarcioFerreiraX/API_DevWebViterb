package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.Edicao;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.repository.EdicaoRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/edicoes")
public class EdicaoController {

	@Autowired
	private EdicaoRepository edicaoRepository;

	@Autowired
	private UserRepository userRepository;

	// Listar todas as edições
	@GetMapping
	public List<Edicao> getAllEdicoes() {
		return edicaoRepository.findAll();
	}

	// Buscar edição por ID
	@GetMapping("/{id}")
	public ResponseEntity<Edicao> getEdicaoById(@PathVariable(value = "id") Long edicaoId) throws ResourceNotFoundException {
		Edicao edicao = edicaoRepository.findById(edicaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Edição não encontrada com o ID: " + edicaoId));
		return ResponseEntity.ok().body(edicao);
	}

	// Criar uma nova edição
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
	public Edicao createEdicao(@Valid @RequestBody Edicao edicao) {
		return edicaoRepository.save(edicao);
	}

	// Atualizar uma edição
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
	public ResponseEntity<Edicao> updateEdicao(@PathVariable(value = "id") Long edicaoId,
											   @Valid @RequestBody Edicao detalhesEdicao) throws ResourceNotFoundException {
		Edicao existingEdicao = edicaoRepository.findById(edicaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Edição não encontrada com o ID: " + edicaoId));

		existingEdicao.setNumero(detalhesEdicao.getNumero());
		existingEdicao.setAno(detalhesEdicao.getAno());
		existingEdicao.setDataInicial(detalhesEdicao.getDataInicial());
		existingEdicao.setDataFinal(detalhesEdicao.getDataFinal());
		existingEdicao.setCidade(detalhesEdicao.getCidade());
		final Edicao updatedEdicao = edicaoRepository.save(existingEdicao);
		return ResponseEntity.ok(updatedEdicao);
	}

	// Excluir uma edição
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
	public ResponseEntity<?> deleteEdicao(@PathVariable(value = "id") Long edicaoId) throws ResourceNotFoundException {
		Edicao edicao = edicaoRepository.findById(edicaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Edição não encontrada com o ID: " + edicaoId));

		edicaoRepository.delete(edicao);
		return ResponseEntity.ok().build();
	}

	// Associar um organizador a uma edição
	@PutMapping("/{edicaoId}/organizador/{organizadorId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Edicao> setOrganizadorToEdicao(
			@PathVariable(value = "edicaoId") Long edicaoId,
			@PathVariable(value = "organizadorId") Long organizadorId) {
		Edicao edicao = edicaoRepository.findById(edicaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Edição não encontrada com o ID: " + edicaoId));

		User organizador = userRepository.findById(organizadorId)
				.orElseThrow(() -> new ResourceNotFoundException("Organizador não encontrado com o ID: " + organizadorId));

		edicao.setOrganizador(organizador);
		final Edicao updatedEdicao = edicaoRepository.save(edicao);
		return ResponseEntity.ok(updatedEdicao);
	}
}
