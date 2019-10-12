package br.org.crea.commons.models.financeiro.enuns;

public enum FinFase {
	
	COBRANCA_SIMPLES(new Long(0)),
	FASE_AMIGAVEL(new Long(1)),
	FASE_JUDICIAL(new Long(2));
	
	public Long id;
	
	FinFase(Long id){
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	
}
