package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SIACOL_STATUS_ITEM_PAUTA")
public class StatusItemPauta {
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="NOME")
	private String nome;

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
	
	
	
	

}
