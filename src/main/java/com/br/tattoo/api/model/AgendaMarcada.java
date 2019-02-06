package com.br.tattoo.api.model;

import java.time.LocalDate;

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
@Table(name = "AgendaMarcada")
public class AgendaMarcada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "hora")
	private Horarios hora;
	@Column(name="data")
	@NotNull
	private LocalDate data;
//	@OneToOne
//	@JoinColumn(name="id")
//	private Cliente cliente;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Horarios getHora() {
		return hora;
	}
	public void setHora(Horarios hora) {
		this.hora = hora;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	
//	public Cliente getCliente() {
//		return cliente;
//	}
//	public void setCliente(Cliente cliente) {
//		this.cliente = cliente;
//	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		AgendaMarcada other = (AgendaMarcada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
