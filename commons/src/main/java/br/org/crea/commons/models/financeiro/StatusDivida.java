package br.org.crea.commons.models.financeiro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "FIN_STATUS_DIVIDA")
public class StatusDivida {
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="QUITADO")
	private Integer quitado;

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

	public Integer getQuitado() {
		return quitado;
	}

	public void setQuitado(Integer quitado) {
		this.quitado = quitado;
	}
	
	
	
		

}
