package br.org.crea.commons.models.cadastro.dtos;

public class CertidaoDto {
	
	private Long ano;
	private String numero;
	private String tipo;
	private String dataCertidao;
	private String formaEmissao;
	

	public Long getAno() {
		return ano;
	}
	public void setAno(Long ano) {
		this.ano = ano;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDataCertidao() {
		return dataCertidao;
	}
	public void setDataCertidao(String dataCertidao) {
		this.dataCertidao = dataCertidao;
	}
	public String getFormaEmissao() {
		return formaEmissao;
	}
	public void setFormaEmissao(String formaEmissao) {
		this.formaEmissao = formaEmissao;
	}
	
	
	

}
