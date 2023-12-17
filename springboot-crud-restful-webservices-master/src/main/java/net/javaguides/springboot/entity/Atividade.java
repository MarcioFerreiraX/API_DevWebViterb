package net.javaguides.springboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "atividades")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Atividade {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atividade_seq")
	@SequenceGenerator(name = "atividade_seq", sequenceName = "atividade_seq", allocationSize = 1)
	private long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private TipoAtividade tipo;

	@Column(name = "descricao", nullable = false, length = 1000)
	private String descricao;

	@Column(name = "data", nullable = false)
	private LocalDateTime data;

	@Column(name = "horario_inicial", nullable = false)
	private LocalDateTime horarioInicial;

	@Column(name = "horario_final", nullable = false)
	private LocalDateTime horarioFinal;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "atividades_favoritas",
			joinColumns = @JoinColumn(name = "atividade_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Set<User> usuariosFavoritos = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "edicao_id")
	@JsonBackReference
	private Edicao edicao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "espaco_id")
	private Espaco espaco;

	// Construtores
	public Atividade() {
	}

	public Atividade(String nome, TipoAtividade tipo, String descricao, LocalDateTime data, LocalDateTime horarioInicial, LocalDateTime horarioFinal) {
		this.nome = nome;
		this.tipo = tipo;
		this.descricao = descricao;
		this.data = data;
		this.horarioInicial = horarioInicial;
		this.horarioFinal = horarioFinal;
	}

	// Getters e Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoAtividade getTipo() {
		return tipo;
	}

	public void setTipo(TipoAtividade tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public LocalDateTime getHorarioInicial() {
		return horarioInicial;
	}

	public void setHorarioInicial(LocalDateTime horarioInicial) {
		this.horarioInicial = horarioInicial;
	}

	public LocalDateTime getHorarioFinal() {
		return horarioFinal;
	}

	public void setHorarioFinal(LocalDateTime horarioFinal) {
		this.horarioFinal = horarioFinal;
	}

	public Edicao getEdicao() {
		return edicao;
	}

	public void setEdicao(Edicao edicao) {
		this.edicao = edicao;
	}

	public Espaco getEspaco() {
		return espaco;
	}

	public void setEspaco(Espaco espaco) {
		this.espaco = espaco;
	}

	// Enum para o tipo de atividade
	public enum TipoAtividade {
		PALESTRA,
		PAINEL,
		SESSAO_TECNICA,
		WORKSHOP,
		// outros tipos conforme necessário
	}

	public Set<User> getUsuariosFavoritos() {
		return usuariosFavoritos;
	}

	public void addUsuarioFavorito(User user) {
		this.usuariosFavoritos.add(user);
		user.getAtividadesFavoritas().add(this);
	}

	public void removeUsuarioFavorito(User user) {
		this.usuariosFavoritos.remove(user);
		user.getAtividadesFavoritas().remove(this);
	}
}
