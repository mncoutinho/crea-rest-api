package br.org.crea.commons.models.protocolo.enuns;

public enum SituacaoProtocoloEnum {

	EM_TRAMITACAO(new Long(0), "Em Tramitação");
	
	public final Long id;
	public final String descricao;
	
	private  SituacaoProtocoloEnum(Long id, String descricao) {
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
