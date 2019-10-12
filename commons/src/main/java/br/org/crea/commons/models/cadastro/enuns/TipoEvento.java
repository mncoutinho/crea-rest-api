package br.org.crea.commons.models.cadastro.enuns;

public enum TipoEvento {
	
	ENTREGA_DE_CARTEIRA(new Long(1)),
	OPCAO_PELO_CATALOGO_DO_CREA_RJ(new Long(250));
	
	
	private final Long id;
	
	TipoEvento(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	
	

}
