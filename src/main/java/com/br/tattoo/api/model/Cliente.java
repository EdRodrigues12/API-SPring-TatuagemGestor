package com.br.tattoo.api.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="nome")
	@NotNull
	private String nome;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "idTelefone")
	private Telefone idTelefone;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "idAgendamarcada")
	private AgendaMarcada idAgendaMarcada;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "idTatuagem")
	private Tatuagem idTatuagem;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Telefone getidTelefone() {
		return idTelefone;
	}
	public void setidTelefone(Telefone idTelefone) {
		this.idTelefone = idTelefone;
	}
	public AgendaMarcada getidAgendaMarcada() {
		return idAgendaMarcada;
	}
	public void setAgendaMarcada(AgendaMarcada idAgendaMarcada) {
		this.idAgendaMarcada = idAgendaMarcada;
	}
	public Tatuagem getidTatuagem() {
		return idTatuagem;
	}
	public void setTatuagem(Tatuagem idTatuagem) {
		this.idTatuagem = idTatuagem;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAgendaMarcada == null) ? 0 : idAgendaMarcada.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (idAgendaMarcada == null) {
			if (other.idAgendaMarcada != null)
				return false;
		} else if (!idAgendaMarcada.equals(other.idAgendaMarcada))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
