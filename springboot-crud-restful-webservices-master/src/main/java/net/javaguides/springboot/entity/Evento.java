package net.javaguides.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "eventos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evento_seq")
	@SequenceGenerator(name = "evento_seq", sequenceName = "evento_seq", allocationSize = 1)
	private long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "sigla", nullable = false, unique = true)
	private String sigla;

	@Column(name = "descricao", nullable = false, length = 500)
	private String descricao;

	@Column(name = "caminho", nullable = false, unique = true)
	private String caminho;

	@OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<Edicao> edicoes = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "organizador_id")
	private User organizador;

	// Construtores
	public Evento() {
	}

	public Evento(String nome, String sigla, String descricao, String caminho) {
		this.nome = nome;
		this.sigla = sigla;
		this.descricao = descricao;
		this.caminho = caminho;
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public Set<Edicao> getEdicoes() {
		return edicoes;
	}

	public void setEdicoes(Set<Edicao> edicoes) {
		this.edicoes = edicoes;
	}

	// Métodos convenientes para adicionar ou remover uma edição
	public void addEdicao(Edicao edicao) {
		edicoes.add(edicao);
		edicao.setEvento(this);
	}

	public void removeEdicao(Edicao edicao) {
		edicoes.remove(edicao);
		edicao.setEvento(null);
	}

	public User getOrganizador() {
		return organizador;
	}

	public void setOrganizador(User organizador) {
		this.organizador = organizador;
	}
}
