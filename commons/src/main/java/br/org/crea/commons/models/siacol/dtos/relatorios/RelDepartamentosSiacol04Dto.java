package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelDepartamentosSiacol04Dto {
	
	private String nome;
	
	private String qtd;
	
	private String qtdJulgados;
	
	private String qtdNaoJulgados;
	
	List<RelDetalhadoSiacol04Dto> protocolos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getQtd() {
		return qtd;
	}

	public void setQtd(String qtd) {
		this.qtd = qtd;
	}

	public List<RelDetalhadoSiacol04Dto> getProtocolos() {
		return protocolos;
	}

	public void setProtocolos(List<RelDetalhadoSiacol04Dto> protocolos) {
		this.protocolos = protocolos;
	}

	public String getQtdJulgados() {
		return qtdJulgados;
	}

	public void setQtdJulgados(String qtdJulgados) {
		this.qtdJulgados = qtdJulgados;
	}

	public String getQtdNaoJulgados() {
		return qtdNaoJulgados;
	}

	public void setQtdNaoJulgados(String qtdNaoJulgados) {
		this.qtdNaoJulgados = qtdNaoJulgados;
	}	

}
