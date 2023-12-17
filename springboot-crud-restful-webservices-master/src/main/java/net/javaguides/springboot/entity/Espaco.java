package net.javaguides.springboot.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "espacos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Espaco {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "espaco_seq")
	@SequenceGenerator(name = "espaco_seq", sequenceName = "espaco_seq", allocationSize = 1)
	private long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "localizacao")
	private String localizacao;

	@Column(name = "capacidade")
	private int capacidade;

	@ElementCollection
	@CollectionTable(name = "espaco_recursos", joinColumns = @JoinColumn(name = "espaco_id"))
	@Column(name = "recurso")
	private Set<String> recursos = new HashSet<>();

	@OneToMany(mappedBy = "espaco", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Atividade> atividades = new HashSet<>();

	// Construtores
	public Espaco() {
	}

	public Espaco(String nome, String localizacao, int capacidade) {
		this.nome = nome;
		this.localizacao = localizacao;
		this.capacidade = capacidade;
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

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public Set<String> getRecursos() {
		return recursos;
	}

	public void setRecursos(Set<String> recursos) {
		this.recursos = recursos;
	}

	public Set<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(Set<Atividade> atividades) {
		this.atividades = atividades;
	}

	// Métodos convenientes para adicionar ou remover atividades
	public void addAtividade(Atividade atividade) {
		atividades.add(atividade);
		atividade.setEspaco(this);
	}

	public void removeAtividade(Atividade atividade) {
		atividades.remove(atividade);
		atividade.setEspaco(null);
	}
}
