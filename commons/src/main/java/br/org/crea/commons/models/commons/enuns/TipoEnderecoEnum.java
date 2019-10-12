package br.org.crea.commons.models.commons.enuns;

import br.org.crea.commons.models.commons.TipoEndereco;

public enum TipoEnderecoEnum {
	RESIDENCIAL(1L),
	COMERCIAL(2L),
	POSTAL(3L),
	OFICIAL(4L),
	MATRIZ(5L),
	FILIAL(6L),
	CONTRATANTE(7L),
	OBRASERVICO(8L),
	VISTO(9L);
	
	private final Long id;
	
	

	private TipoEnderecoEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public static TipoEndereco getTipoEndereco(TipoEnderecoEnum tipoEnderecoEnum){
		TipoEndereco tipoEndereco = new TipoEndereco();
		tipoEndereco.setId(tipoEnderecoEnum.getId());
		
		return tipoEndereco;
	}
	
	
}
