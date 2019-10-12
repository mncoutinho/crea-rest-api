package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAD_ESCOLARIDADES")
public class Escolaridade {
	
	@Id
	@Column(name="CODIGO")
	private Long id;

	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="NIVEL")
	private Long nivel;

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

	public Long getNivel() {
		return nivel;
	}

	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}
	
	

}
