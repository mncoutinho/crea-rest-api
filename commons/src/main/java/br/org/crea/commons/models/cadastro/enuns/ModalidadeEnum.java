package br.org.crea.commons.models.cadastro.enuns;

public enum ModalidadeEnum {
	NAO_INFORMADO(0L),
	CIVIL(1L),
	ELETRICA(2L),
	MECANICA_METALURGIA(3L),
	ARQUITETURA(4L),
	AGRONOMIA(5L),
	GEOLOGIA_MINAS(6L),
	SEGURANCA(7L),
	QUIMICA(8L),
	AGRIMENSURA(9L);
	
	private final Long id;
	
	ModalidadeEnum(Long id){
		this.id = id;
	}


	public Long getId() {
		return id;
	}	
}
