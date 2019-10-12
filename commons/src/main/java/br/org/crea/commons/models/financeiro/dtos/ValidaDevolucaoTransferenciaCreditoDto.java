package br.org.crea.commons.models.financeiro.dtos;

public class ValidaDevolucaoTransferenciaCreditoDto {
	
	private Long idDivida;
	
	private String nossoNumero;
	
	private Long idPessoa;
	
	private String tipoPessoa;
	
	private String cpfOuCnpj;

	public Long getIdDivida() {
		return idDivida;
	}

	public void setIdDivida(Long idDivida) {
		this.idDivida = idDivida;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
}
