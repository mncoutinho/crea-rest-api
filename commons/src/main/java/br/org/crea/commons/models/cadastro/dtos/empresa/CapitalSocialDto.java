package br.org.crea.commons.models.cadastro.dtos.empresa;

import java.io.Serializable;

public class CapitalSocialDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2532624971022171357L;

	private String codigo;
	
	private String tipoCapitalSocial;
	
	private String dataInclusao;
	
	private String dataAlteracao;
	
	private String dataJuntaComercial;
	
	private String dataReceitaFederal;
	
	private String dataIntegralizacao;
	
	private String faixaCapital;
	
	private String valorCapital;
	
	private String valorCapitalMoedaCorrente;
	
	private String valorCapitalIntegralizado;
	
	private String valorCapitalAIntegralizar;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipoCapitalSocial() {
		return tipoCapitalSocial;
	}

	public void setTipoCapitalSocial(String tipoCapitalSocial) {
		this.tipoCapitalSocial = tipoCapitalSocial;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(String dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getDataJuntaComercial() {
		return dataJuntaComercial;
	}

	public void setDataJuntaComercial(String dataJuntaComercial) {
		this.dataJuntaComercial = dataJuntaComercial;
	}

	public String getDataReceitaFederal() {
		return dataReceitaFederal;
	}

	public void setDataReceitaFederal(String dataReceitaFederal) {
		this.dataReceitaFederal = dataReceitaFederal;
	}

	public String getDataIntegralizacao() {
		return dataIntegralizacao;
	}

	public void setDataIntegralizacao(String dataIntegralizacao) {
		this.dataIntegralizacao = dataIntegralizacao;
	}

	public String getFaixaCapital() {
		return faixaCapital;
	}

	public void setFaixaCapital(String faixaCapital) {
		this.faixaCapital = faixaCapital;
	}

	public String getValorCapital() {
		return valorCapital;
	}

	public void setValorCapital(String valorCapital) {
		this.valorCapital = valorCapital;
	}

	public String getValorCapitalMoedaCorrente() {
		return valorCapitalMoedaCorrente;
	}

	public void setValorCapitalMoedaCorrente(String valorCapitalMoedaCorrente) {
		this.valorCapitalMoedaCorrente = valorCapitalMoedaCorrente;
	}

	public String getValorCapitalIntegralizado() {
		return valorCapitalIntegralizado;
	}

	public void setValorCapitalIntegralizado(String valorCapitalIntegralizado) {
		this.valorCapitalIntegralizado = valorCapitalIntegralizado;
	}

	public String getValorCapitalAIntegralizar() {
		return valorCapitalAIntegralizar;
	}

	public void setValorCapitalAIntegralizar(String valorCapitalAIntegralizar) {
		this.valorCapitalAIntegralizar = valorCapitalAIntegralizar;
	}

	public String getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(String dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
}
