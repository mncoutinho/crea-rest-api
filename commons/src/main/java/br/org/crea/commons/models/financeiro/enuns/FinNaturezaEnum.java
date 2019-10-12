package br.org.crea.commons.models.financeiro.enuns;

public enum FinNaturezaEnum {

	EXPEDICAO_CARTEIRA(new Long(407), "EXPEDICAO DE CARTEIRA OU 2 VIA");
	
	public final Long id;
	public final String descricao;
	
	private  FinNaturezaEnum(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
