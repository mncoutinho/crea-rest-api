package br.org.crea.commons.models.portal.dto;

public class AtendimentoDto {
	
	
	private Long numeroChamado;
	
	private int tempoEspera;
	
	private int cordialidade;
	
	private int clareza;
	
	private int orientacao;
	
	private String sugestao;

	public Long getNumeroChamado() {
		return numeroChamado;
	}

	public void setNumeroChamado(Long numeroChamado) {
		this.numeroChamado = numeroChamado;
	}

	public int getTempoEspera() {
		return tempoEspera;
	}

	public void setTempoEspera(int tempoEspera) {
		this.tempoEspera = tempoEspera;
	}

	public int getCordialidade() {
		return cordialidade;
	}

	public void setCordialidade(int cordialidade) {
		this.cordialidade = cordialidade;
	}

	public int getClareza() {
		return clareza;
	}

	public void setClareza(int clareza) {
		this.clareza = clareza;
	}

	public int getOrientacao() {
		return orientacao;
	}

	public void setOrientacao(int orientacao) {
		this.orientacao = orientacao;
	}

	public String getSugestao() {
		return sugestao;
	}

	public void setSugestao(String sugestao) {
		this.sugestao = sugestao;
	}
	
	

}
