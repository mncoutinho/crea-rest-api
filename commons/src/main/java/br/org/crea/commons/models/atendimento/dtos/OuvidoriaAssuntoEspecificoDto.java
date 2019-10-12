package br.org.crea.commons.models.atendimento.dtos;

import java.util.Date;

public class OuvidoriaAssuntoEspecificoDto {

	private Long id;
	
	private String descricao;
	
	private Date dataAlteracao;
	
	private Long assuntosGerais;
	
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


	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Long getAssuntosGerais() {
		return assuntosGerais;
	}

	public void setAssuntosGerais(Long assuntosGerais) {
		this.assuntosGerais = assuntosGerais;
	}

	
	
	
		
}
