package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelSiacol04Dto {
	
	private String assunto;
	
	private String assuntoConfea;
	
	private Long codigoAssunto;
	
	private Long codigoAssuntoConfea;
	
	private String descricaoAssunto;
	
	private String descricaoAssuntoConfea;
	
	private int qtdNaoClassificado;
	
	private List<RelDepartamentosSiacol04Dto> departamentos;
	
	private int total;

	public List<RelDepartamentosSiacol04Dto> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<RelDepartamentosSiacol04Dto> departamentos) {
		this.departamentos = departamentos;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getAssuntoConfea() {
		return assuntoConfea;
	}

	public void setAssuntoConfea(String assuntoConfea) {
		this.assuntoConfea = assuntoConfea;
	}

	public Long getCodigoAssunto() {
		return codigoAssunto;
	}

	public void setCodigoAssunto(Long codigoAssunto) {
		this.codigoAssunto = codigoAssunto;
	}

	public Long getCodigoAssuntoConfea() {
		return codigoAssuntoConfea;
	}

	public void setCodigoAssuntoConfea(Long codigoAssuntoConfea) {
		this.codigoAssuntoConfea = codigoAssuntoConfea;
	}

	public String getDescricaoAssunto() {
		return descricaoAssunto;
	}

	public void setDescricaoAssunto(String descricaoAssunto) {
		this.descricaoAssunto = descricaoAssunto;
	}

	public String getDescricaoAssuntoConfea() {
		return descricaoAssuntoConfea;
	}

	public void setDescricaoAssuntoConfea(String descricaoAssuntoConfea) {
		this.descricaoAssuntoConfea = descricaoAssuntoConfea;
	}

	public int getQtdNaoClassificado() {
		return qtdNaoClassificado;
	}

	public void setQtdNaoClassificado(int qtdNaoClassificado) {
		this.qtdNaoClassificado = qtdNaoClassificado;
	}
	
}
