package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelDepartamentosSiacolDto {
	
	private String nome;
	
	private int qtd;
	
	List<String> protocolos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public List<String> getProtocolos() {
		return protocolos;
	}

	public void setProtocolos(List<String> protocolos) {
		this.protocolos = protocolos;
	}
	
	

}
