package br.org.crea.commons.models.atendimento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OUVI_TIPOS_DEMANDA")
public class OuvidoriaTipoDemanda {

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
