package br.org.crea.commons.models.commons.enuns;

public enum TipoAcaoEnum {
	
	BAIXA_DE_RESPONSAVEL_TECNICO(new Long(76), "Baixa de Responsável Técnico "),
	BAIXA_DE_QUADRO_TECNICO(new Long(75), "Baixa de Quadro Técnico"),
	BAIXA_ART(new Long(14), "Baixa Art"),
	INCLUSAO_ART(new Long(27), "Inclusão Art"),
	INCLUIDO_NO_CATALOGO_PROFISSIONAL(new Long(95), "Incluído no catálogo profissional"),
	EXCLUIDO_DO_CATALOGO_PROFISSIONAL(new Long(96), "Excluído no catálogo profissional");
	
	public final Long id;
	public final String descricao;
	
	private  TipoAcaoEnum(Long id, String descricao) {
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
