package br.org.crea.commons.models.cadastro.enuns;

public enum TipoGrupoEmpresa {
	
	AGENCIA			( new Long(1), new Long(2), new Long(0)),
	ESCRITORIO		( new Long(2), new Long(2), new Long(0)),
	FILIAL			( new Long(3), new Long(2), new Long(1)),
	MATRIZ			( new Long(4), new Long(1), new Long(2)),
	REPRESENTACAO	( new Long(5), new Long(2), new Long(0)),
	SUCURSAL		( new Long(6), new Long(2), new Long(0)),
	NAOINFORMADO	( new Long(7), new Long(1), new Long(0));
	
	private final Long codigo;
	
	private final Long divisor;
	
	private final Long rpj;
	
	private TipoGrupoEmpresa(Long codigo, Long divisor, Long rpj) {
		
		this.codigo = codigo;
		this.divisor = divisor;
		this.rpj = rpj;
	}

	public Long getCodigo() {
		return codigo;
	}

	public Long getDivisor() {
		return divisor;
	}

	public Long getRpj() {
		return rpj;
	}

}
