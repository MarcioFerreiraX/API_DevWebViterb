package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// Listar todos os usuários
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// Buscar usuário por ID
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		return ResponseEntity.ok().body(user);
	}

	// Criar um novo usuário
	@PostMapping
	public User createUser(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}

	// Atualizar um usuário
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") long userId,
										   @Valid @RequestBody User userDetails) {
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));

		existingUser.setLogin(userDetails.getLogin());
		existingUser.setEmail(userDetails.getEmail());
		existingUser.setNome(userDetails.getNome());
		existingUser.setAfiliacao(userDetails.getAfiliacao());
		final User updatedUser = userRepository.save(existingUser);
		return ResponseEntity.ok(updatedUser);
	}

	// Excluir um usuário por ID
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") long userId) {
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
		userRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}
}
