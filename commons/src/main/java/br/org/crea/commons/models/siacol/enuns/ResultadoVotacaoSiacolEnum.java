package br.org.crea.commons.models.siacol.enuns;

public enum ResultadoVotacaoSiacolEnum {

	A_FAVOR_DO_RELATOR(new Long(1), "A Favor do Relator"),
	CONTRA_RELATOR(new Long(2), "Contra o Relator"),
	A_FAVOR_DE_VISTAS(new Long(3), "A Favor de Vistas"),
	HOMOLOGACAO_PROFISSIONAL(new Long(4), "Homologacao profissional"),
	ASSUNTO_VOTADO(new Long(5), "Assunto votado");
	
	private Long id;
	private String nome;
	
	private ResultadoVotacaoSiacolEnum(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public static Long getIdBy(ResultadoVotacaoSiacolEnum tipo){
		
		for(ResultadoVotacaoSiacolEnum s : ResultadoVotacaoSiacolEnum.values()){
			if(s.equals(tipo)){
				return s.id;
			}
		}
		
		return null;
	}
	
	public static String getNomeBy(Long long1){
		
		for(ResultadoVotacaoSiacolEnum s : ResultadoVotacaoSiacolEnum.values()){
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
