package br.org.crea.commons.models.art.enuns;

public enum TipoComplementoContratoArt {
	
	CONCRETO(new Long(34)),
	APARELHO_ELETRICO_ELETRONICO(new Long(199)),
	RESP_POR_TODA_ATV_TEC_EXECUTADA_PELA_PJ(new Long(189)),
	PROF_DO_QT_DA_EMPRESA(new Long(190));
		
	private final Long id;
	
	TipoComplementoContratoArt(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
