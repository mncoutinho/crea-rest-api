package br.org.crea.commons.models.commons.enuns;

public enum ModuloSistema {
	
	CADASTRO(new Long(1), "Cadastro", "Módulo de Cadastro"),
	SIACOL(new Long(2), "Siacol", "Módulo Siacol"),
	CORPORATIVO(new Long(3), "Corporativo", "Módulo Corporativo"),
	FINANCEIRO(new Long(4), "Financeiro", "Módulo Financeiro"),
	ATENDIMENTO(new Long(5), "Atendimento", "Módulo de Atendimento"),
	PROTOCOLO(new Long(6), "Protocolo", "Módulo de Protocolo"),
	INSTITUICAO(new Long(6), "Cadastro", "Módulo de Cadastro"),
	ART(new Long(7), "ART", "Módulo de ART");

	
	private Long id;
	private String nome;
	private String descricao;
	
	private ModuloSistema(Long id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public static Long getIdBy(ModuloSistema modulo){
		
		for(ModuloSistema s : ModuloSistema.values()){
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
