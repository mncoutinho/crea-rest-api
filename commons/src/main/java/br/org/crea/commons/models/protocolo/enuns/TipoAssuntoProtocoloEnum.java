package br.org.crea.commons.models.protocolo.enuns;

public enum TipoAssuntoProtocoloEnum {

	SEGUNDA_VIA_CARTEIRA(new Long(1006), "SEGUNDA VIA DE CARTEIRA DE IDENTIDADE PROFISSIONAL"),
	BAIXA_DE_RESPONSAVEL_TECNICO(new Long(2011), "BAIXA DE RESPONSÁVEL TECNICO");
	
	public final Long id;
	public final String descricao;
	
	private  TipoAssuntoProtocoloEnum(Long id, String descricao) {
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
