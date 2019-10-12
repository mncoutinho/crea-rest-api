package br.org.crea.commons.models.protocolo.enuns;

public enum TipoProtocoloEnum {

	LEIGO(new Long(0), "Processo de leigo"),
	PROFISSIONAL(new Long(1), "Processo de Profissional"), 
	EMPRESA(new Long(2), "Processo de Empresa"), 
	AUTOINFRACAO(new Long(3), "Processo de Auto de Infração"), 
	ADMINISTRATIVO_FINANCEIRO(new Long(4), "Processo Administrativo / Financeiro"),
	OUTROS_TIPOS(new Long(5), "Processo de Outros Tipos"),             
	NOTIFICACAO_OFICIO(new Long(6), "Processo de Notificação de Ofício"),       
	PROTOCOLO(new Long(7), "Protocolo vinculado a processo inicial"),
	ENTIDADE_CLASSE_ENSINO(new Long(8), "Processo de Entidade de Classe"),
	AUTOINFRACAO_EXTERNO(new Long(9), "Processo de Auto de Infração Externo");
	
	public final Long digito;
	public final String descricao;
	
	private  TipoProtocoloEnum(Long digito, String descricao) {
		this.digito = digito;
		this.descricao = descricao;
	}

	public Long getDigito() {
		return digito;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
