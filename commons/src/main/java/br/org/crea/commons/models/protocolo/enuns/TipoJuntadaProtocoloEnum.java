package br.org.crea.commons.models.protocolo.enuns;

public enum TipoJuntadaProtocoloEnum {
	
	ANEXACAO(new Long(0), "Anexação de protocolo ao processo"),
	APENSACAO(new Long(1), "Apensação de protocolo ao processo"), 
	DESANEXACAO(new Long(2), "Desanexação de protocolo do processo"), 
	DESAPENSACAO(new Long(3), "Desapensação de protocolo do processo");
	
	public final Long codigo;
	public final String descricao;
	
	private  TipoJuntadaProtocoloEnum(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}
