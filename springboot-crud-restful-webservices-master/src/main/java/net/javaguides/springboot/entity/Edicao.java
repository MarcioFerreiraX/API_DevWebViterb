package net.javaguides.springboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "edicoes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Edicao {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edicao_seq")
	@SequenceGenerator(name = "edicao_seq", sequenceName = "edicao_seq", allocationSize = 1)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organizador_id")
	private User organizador;

	@Column(name = "numero", nullable = false)
	private int numero;

	@Column(name = "ano", nullable = false)
	private int ano;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_inicial", nullable = false)
	private Date dataInicial;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_final", nullable = false)
	private Date dataFinal;

	@Column(name = "cidade", nullable = false)
	private String cidade;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "evento_id")
	@JsonBackReference
	private Evento evento;

	@OneToMany(mappedBy = "edicao", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<Atividade> atividades = new HashSet<>();

	// Construtores
	public Edicao() {
	}

	public Edicao(int numero, int ano, Date dataInicial, Date dataFinal, String cidade) {
		this.numero = numero;
		this.ano = ano;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.cidade = cidade;
	}

	// Getters e Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Set<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(Set<Atividade> atividades) {
		this.atividades = atividades;
	}

	public User getOrganizador() {
		return organizador;
	}

	public void setOrganizador(User organizador) {
		this.organizador = organizador;
	}

	// Métodos convenientes para adicionar ou remover uma atividade
	public void addAtividade(Atividade atividade) {
		atividades.add(atividade);
		atividade.setEdicao(this);
	}

	public void removeAtividade(Atividade atividade) {
		atividades.remove(atividade);
		atividade.setEdicao(null);
	}


}
