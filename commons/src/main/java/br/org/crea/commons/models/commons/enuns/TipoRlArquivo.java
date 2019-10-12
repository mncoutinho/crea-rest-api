package br.org.crea.commons.models.commons.enuns;

public enum TipoRlArquivo {
	
	FIS_RF_ONLINE(new Long(1), "RF Online", "Relatório de Fiscalização"),
	SIACOL(new Long(2), "Siacol", "Módulo Siacol"),
	FIS_AUTOINFRACAO(new Long(3), "Auto de Infracao", "Auto de Infração");

	
	private Long id;
	private String nome;
	private String descricao;
	
	private TipoRlArquivo(Long id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public static Long getIdBy(TipoRlArquivo modulo){
		
		for(TipoRlArquivo s : TipoRlArquivo.values()){
			if(s.equals(modulo)){
				return s.id;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
