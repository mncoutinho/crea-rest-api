package br.org.crea.commons.models.art.enuns;

public enum TipoEspecificacaoContratoArt {
	
	CONSERVACAO   (new Long(12)),
	FABRICACAO   (new Long(27)),
	FORNECIMENTO (new Long(29)); 
	
	private final Long id;
	
	TipoEspecificacaoContratoArt(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
