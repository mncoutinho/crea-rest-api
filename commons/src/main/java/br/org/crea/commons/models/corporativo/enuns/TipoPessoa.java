package br.org.crea.commons.models.corporativo.enuns;


public enum TipoPessoa {
	
	PROFISSIONAL	(new Long(1)),
	EMPRESA			(new Long(2)),
	FORMANDO		(new Long(3)),
	ENTIDADE		(new Long(4)),
	ESCOLA			(new Long(5)),
	LEIGOPF			(new Long(6)),
	LEIGOPJ			(new Long(7)),
	FUNCIONARIO		(new Long(8)),
	PESSOAFISICA	(new Long(9)),
	PESSOAJURIDICA	(new Long(10));

	private final Long ordem;
	
	private TipoPessoa(Long ordem){
		this.ordem = ordem;
	}

	public Long getOrdem() {
		return ordem;
	}

}
