package br.org.crea.commons.models.protocolo.enuns;

public enum TipoAssuntoEnum {
	AUTUA("Autuar(gerar) um novo protocolo com o mesmo número do processo"),
	JUNTA("Vincula um protocolo a um processo do mesmo interessado e do mesmo tipo já existente"),
	PROTOCOLO("Somente um protocolo sera gerado, sem vinculo a um processo"),
	AGENDAMENTO("Agendamento para atender cliente externo");
	
	public final String descricao;
	
	private  TipoAssuntoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
