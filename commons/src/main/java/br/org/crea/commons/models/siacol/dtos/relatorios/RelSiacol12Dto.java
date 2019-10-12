package br.org.crea.commons.models.siacol.dtos.relatorios;

public class RelSiacol12Dto {
	
	private String departamento;
	
	private int qtdDecisoesFavoraveis;
	
	private int qtdDecisoesDesfavoraveis;
	
	private int qtdDecisoesHomologacao;
	
	private int qtdDecisoesAssunto;
	
	private int qtdNaoClassificados;
	
	private int qtdTotalDepartamento;

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public int getQtdDecisoesFavoraveis() {
		return qtdDecisoesFavoraveis;
	}

	public void setQtdDecisoesFavoraveis(int qtdDecisoesFavoraveis) {
		this.qtdDecisoesFavoraveis = qtdDecisoesFavoraveis;
	}

	public int getQtdDecisoesDesfavoraveis() {
		return qtdDecisoesDesfavoraveis;
	}

	public void setQtdDecisoesDesfavoraveis(int qtdDecisoesDesfavoraveis) {
		this.qtdDecisoesDesfavoraveis = qtdDecisoesDesfavoraveis;
	}

	public int getQtdDecisoesHomologacao() {
		return qtdDecisoesHomologacao;
	}

	public void setQtdDecisoesHomologacao(int qtdDecisoesHomologacao) {
		this.qtdDecisoesHomologacao = qtdDecisoesHomologacao;
	}

	public int getQtdDecisoesAssunto() {
		return qtdDecisoesAssunto;
	}

	public void setQtdDecisoesAssunto(int qtdDecisoesAssunto) {
		this.qtdDecisoesAssunto = qtdDecisoesAssunto;
	}

	public int getQtdTotalDepartamento() {
		return qtdTotalDepartamento;
	}

	public void setQtdTotalDepartamento(int qtdTotalDepartamento) {
		this.qtdTotalDepartamento = qtdTotalDepartamento;
	}

	public int getQtdNaoClassificados() {
		return qtdNaoClassificados;
	}

	public void setQtdNaoClassificados(int qtdNaoClassificados) {
		this.qtdNaoClassificados = qtdNaoClassificados;
	}
}
