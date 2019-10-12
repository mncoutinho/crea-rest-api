package br.org.crea.commons.models.siacol.enuns;

public enum StatusReuniaoSiacol {

	CADASTRADA (new Long(1), "Cadastrada"),
	ABERTA	   (new Long(2), "Aberta para votação"),
	FECHADA    (new Long(3), "Fechada para votação"),
	CANCELADA  (new Long(4), "Cancelada"),
	PAUSADA  (new Long(5), "Pausada");
	
	private final Long id;
	
	private final String descricao;
	
	private StatusReuniaoSiacol(Long id, String descricao) {
		
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
