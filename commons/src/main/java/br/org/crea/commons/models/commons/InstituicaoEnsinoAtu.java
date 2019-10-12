package br.org.crea.commons.models.commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_INSTI_ENSINO_ATU")
public class InstituicaoEnsinoAtu {
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	

	
}
