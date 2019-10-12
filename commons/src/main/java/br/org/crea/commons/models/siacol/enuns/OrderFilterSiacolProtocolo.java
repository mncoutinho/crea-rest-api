package br.org.crea.commons.models.siacol.enuns;

public enum OrderFilterSiacolProtocolo {
	
	ORDER_BY_ASSUNTO_PROTOCOLO("descricaoAssuntoCorporativo", "Ordenação de Consulta", "Assunto Protocolo"),
	ORDER_BY_ASSUNTO_SIACOL("assuntoSiacol.nome", "Ordenação de Consulta", "Assunto Siacol"),
	ORDER_BY_CLASSIFICACAO("classificacao", "Ordenação de Consulta", "Classificação"),
	ORDER_BY_RELATOR_PROTOCOLO("nomeConselheiroRelator", "Ordenação de Consulta", "Conselheiro"),
	ORDER_BY_PROCESSO_PROTOCOLO("numeroProcesso, numeroProtocolo", "Ordenação de Consulta", "Processo/Protocolo");
	
	private OrderFilterSiacolProtocolo(String campoConsulta, String tipoOrderFilter, String descricao) {
		this.campoConsulta = campoConsulta;
		this.tipoOrderFilter = tipoOrderFilter;
		this.descricao = descricao; 
	}
	
	private final String campoConsulta;
	
	private final String tipoOrderFilter;
	
	private final String descricao;

	public String getCampoConsulta() {
		return campoConsulta;
	}

	public String getTipoOrderFilter() {
		return tipoOrderFilter;
	}

	public String getDescricao() {
		return descricao;
	}
	

}
