package br.org.crea.commons.models.atendimento.enuns;

import br.org.crea.commons.models.commons.TipoAtendimento;

public enum TipoAtendimentoEnum {
	
	HABILITACAO_TITULO_ENTIDADE_FILIADA(new Long(48), "Habilitação de Título Entidades Filiadas"),
	SOLICITACAO_SEGUNDA_VIA_CARTEIRA(new Long(93), "Solicitação de Segunda Via de Carteira");

	private final Long id;
	
	private String descricao;

	private TipoAtendimentoEnum(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public TipoAtendimento getObjeto() {
		TipoAtendimento tipoAtendimento = new TipoAtendimento();
		tipoAtendimento.setCodigo(id);
		
		return tipoAtendimento;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}



}
