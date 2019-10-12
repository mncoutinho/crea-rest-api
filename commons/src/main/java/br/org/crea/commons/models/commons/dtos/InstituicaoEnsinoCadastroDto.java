package br.org.crea.commons.models.commons.dtos;

import java.math.BigDecimal;

public class InstituicaoEnsinoCadastroDto {
	
	private BigDecimal ID;
	
	private String DESCRICAO;
	
	
	public InstituicaoEnsinoCadastroDto() {
		
	}
	
	public InstituicaoEnsinoCadastroDto(BigDecimal ID, String DESCRICAO) {
		super();
		this.ID = ID;
		this.DESCRICAO = DESCRICAO;
	}
	public BigDecimal getID() {
		return ID;
	}
	public void setID(BigDecimal ID) {
		this.ID = ID;
	}

	public String getDESCRICAO() {
		return DESCRICAO;
	}
	public void setDESCRICAO(String DESCRICAO) {
		this.DESCRICAO = DESCRICAO;
	}
	
}
