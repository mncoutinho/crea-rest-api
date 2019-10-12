package br.org.crea.commons.models.siacol.enuns;


public enum TipoReuniaoEnum {
	
	ORDINARIA(new Long(1), "Ordinária"),
	EXTRAORDINARIA(new Long(2), "Extraordinária");
	
	private Long id;
	private String nome;
	
	private TipoReuniaoEnum(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public static Long getIdBy(TipoReuniaoEnum tipo){
		
		for(TipoReuniaoEnum s : TipoReuniaoEnum.values()){
			if(s.equals(tipo)){
				return s.id;
			}
		}
		
		return null;
	}
	
	public static String getNomeBy(Long long1){
		
		for(TipoReuniaoEnum s : TipoReuniaoEnum.values()){
			if(s.id.equals(long1)){
				return s.nome;
			}
		}
		
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
