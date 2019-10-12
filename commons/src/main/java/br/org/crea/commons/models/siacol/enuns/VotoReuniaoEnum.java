package br.org.crea.commons.models.siacol.enuns;

public enum VotoReuniaoEnum {
	
	S("Sim"),
	N("Não"),
	D("Destaque"),
	A("Abstenção"),
	X("Ainda não votou"),
	V("Votando");
	
	private String descricao;

	private VotoReuniaoEnum( String descricao) {
		this.descricao = descricao;
	}
	
	public static String getDescricaoBy(VotoReuniaoEnum voto){
		
		for(VotoReuniaoEnum s : VotoReuniaoEnum.values()){
			if(s.equals(voto)){
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

	public boolean ehDestaque() {
		return this.toString().equals("D");
	}
	
	
}
