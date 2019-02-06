package com.br.tattoo.api.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tatuagem")
public class Tatuagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="imagem_tattoo")
	@NotNull
	private String imagemTattoo;
	@Column(name="caminhoTatuagem")
	private String caminhoTatuagem;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImagemTattoo() {
		return imagemTattoo;
	}
	public String getCaminhoTatuagem() {
		return caminhoTatuagem;
	}
	public void setCaminhoTatuagem(String caminhoTatuagem) {
		this.caminhoTatuagem = caminhoTatuagem;
	}
	public void setImagemTattoo(String imagemTattoo) {
		this.imagemTattoo = imagemTattoo;
	}
	
	
	
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
		Tatuagem other = (Tatuagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
