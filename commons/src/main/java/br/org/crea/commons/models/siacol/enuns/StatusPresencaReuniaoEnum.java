package br.org.crea.commons.models.siacol.enuns;

public enum StatusPresencaReuniaoEnum {
	E("Entregou Crachá"),
	D("Devolveu Crachá"),
	X("Não compareceu");
	
	private String descricao;

	private StatusPresencaReuniaoEnum( String descricao) {
		this.descricao = descricao;
	}
	
	public static String getDescricaoBy(StatusPresencaReuniaoEnum statusPresenca){
		
		for(StatusPresencaReuniaoEnum s : StatusPresencaReuniaoEnum.values()){
			if(s.equals(statusPresenca)){
				return s.descricao;
			}
		}
		
		return null;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
