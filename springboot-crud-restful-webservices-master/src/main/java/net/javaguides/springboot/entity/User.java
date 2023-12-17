package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty
	@Column(name = "login", nullable = false, unique = true)
	private String login;

	@NotEmpty
	@Email
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotEmpty
	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "afiliacao")
	private String afiliacao;

	@NotEmpty
	@Column(name = "senha", nullable = false)
	private String senha;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private Set<String> roles = new HashSet<>();

	// Relacionamento com Evento
	@OneToMany(mappedBy = "organizador", fetch = FetchType.LAZY)
	private Set<Evento> eventosOrganizados = new HashSet<>();

	// Relacionamento com Atividade
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "usuariosFavoritos")
	private Set<Atividade> atividadesFavoritas = new HashSet<>();

	// Construtor padrão
	public User() {
	}

	// Getters e Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAfiliacao() {
		return afiliacao;
	}

	public void setAfiliacao(String afiliacao) {
		this.afiliacao = afiliacao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Set<Evento> getEventosOrganizados() {
		return eventosOrganizados;
	}

	public void setEventosOrganizados(Set<Evento> eventosOrganizados) {
		this.eventosOrganizados = eventosOrganizados;
	}

	public Set<Atividade> getAtividadesFavoritas() {
		return atividadesFavoritas;
	}

	public void setAtividadesFavoritas(Set<Atividade> atividadesFavoritas) {
		this.atividadesFavoritas = atividadesFavoritas;
	}

	// Métodos convenientes para adicionar e remover eventos e atividades favoritas
	public void addEventoOrganizado(Evento evento) {
		eventosOrganizados.add(evento);
		evento.setOrganizador(this);
	}

	public void removeEventoOrganizado(Evento evento) {
		eventosOrganizados.remove(evento);
		evento.setOrganizador(null);
	}

	public void addAtividadeFavorita(Atividade atividade) {
		atividadesFavoritas.add(atividade);
		atividade.getUsuariosFavoritos().add(this);
	}

	public void removeAtividadeFavorita(Atividade atividade) {
		atividadesFavoritas.remove(atividade);
		atividade.getUsuariosFavoritos().remove(this);
	}
}
