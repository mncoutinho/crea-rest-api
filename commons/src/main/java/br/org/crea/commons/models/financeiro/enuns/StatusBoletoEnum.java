package br.org.crea.commons.models.financeiro.enuns;

public enum StatusBoletoEnum {

	VIGENTE(new Long(1), "Vigente", new Long(0));
	
	public final Long id;
	public final String descricao;
	public final Long quitado;
	
	private  StatusBoletoEnum(Long id, String descricao, Long quitado) {
		this.id = id;
		this.descricao = descricao;
		this.quitado = quitado;
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public Long getQuitado() {
		return quitado;
	}
	
}
