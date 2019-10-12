package br.org.crea.commons.models.corporativo.enuns;


public enum SituacaoRegistro {
	
	CANCELADO	(new Long(9)),
	SUSPENSO		(new Long(10)),
	NOVOINSCRITO	(new Long(5));

	private final Long codigo;
	
	private SituacaoRegistro(Long codigo){
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

}
