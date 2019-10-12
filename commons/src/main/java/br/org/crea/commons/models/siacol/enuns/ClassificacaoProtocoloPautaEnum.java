package br.org.crea.commons.models.siacol.enuns;

public enum ClassificacaoProtocoloPautaEnum {
	
	NAO_CLASSIFICADO(new Long(0), "Não classficado"),
	DESFAVORAVEL(new Long(1), "Desfavorável ao interessado"),
	FAVORAVEL(new Long(2), "Favorável ao interessado");
	
	private final Long id;
	
	private final String descricao;
	
	private ClassificacaoProtocoloPautaEnum(Long id, String descricao) {
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
