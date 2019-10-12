package br.org.crea.commons.models.cadastro.enuns;

public enum StatusEvento {
	
	NAO_PUBLICADO(new Long(1)),
	DISPONIVEL(new Long(2));
	
	
	private final Long id;
	
	StatusEvento(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	
	
}
