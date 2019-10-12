package br.org.crea.commons.models.cadastro.dtos;

import java.io.Serializable;

public class HistoricoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String dataInicial;
	
	private String dataFinal;
	
	private String evento;
	
	private String observacao;

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
