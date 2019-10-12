package br.org.crea.commons.models.cadastro.enuns;

import br.org.crea.commons.models.cadastro.Departamento;

public enum DepartamentoEnum {
	
	ARQUIVO_VIRTUAL(new Long(23040502), "ARQUIVO VIRTUAL"),
	ATENDIMENTO_PORTAL(new Long(99999), "ATENDIMENTO/PORTAL");	

	private Long id;
	private String nome;
	
	private DepartamentoEnum(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public static Long getIdBy(DepartamentoEnum tipo){
		
		for(DepartamentoEnum s : DepartamentoEnum.values()){
			if(s.equals(tipo)){
				return s.id;
			}
		}
		return null;
	}
	
	public static String getNomeBy(Long long1){
		
		for(DepartamentoEnum s : DepartamentoEnum.values()){
			if(s.id.equals(long1)){
				return s.nome;
			}
		}
		
		return null;
	}
	
	public Departamento getObjeto(){
		Departamento departamento = new Departamento();
		
		departamento.setId(id);
		
		return departamento;
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
