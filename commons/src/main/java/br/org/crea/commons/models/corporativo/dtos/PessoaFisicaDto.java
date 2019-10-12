package br.org.crea.commons.models.corporativo.dtos;

import java.io.Serializable;

public class PessoaFisicaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PessoaDto pessoa = new PessoaDto();
	
	private String nomePai;
	
	private String nomeMae;
	
	private String nacionalidade;
	
	private String naturalidade;
	
	private String localidadeNaturalidade;
	
	private String estadoCivil;
	
	private String sexo;
	
	private String dataNascimento;
	
	private String portadorDeficiencia;
	
	private String tipoSanguineo;
	
	private String doador;
	
	private String pisPasep;
	
	private String identidade;
	
	private String emissorIdentidade;
	
	private String dataEmissaoIdentidade;
	
	private String tituloEleitoral;
	
	private String zonaEleitoral;
	
	private String secaoEleitoral;
	
	private String localidadeEleitoral;
	
	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
		this.pessoa = pessoa;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getLocalidadeNaturalidade() {
		return localidadeNaturalidade;
	}

	public void setLocalidadeNaturalidade(String localidadeNaturalidade) {
		this.localidadeNaturalidade = localidadeNaturalidade;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getPortadorDeficiencia() {
		return portadorDeficiencia;
	}

	public void setPortadorDeficiencia(String portadorDeficiencia) {
		this.portadorDeficiencia = portadorDeficiencia;
	}

	public String getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public String getDoador() {
		return doador;
	}

	public void setDoador(String doador) {
		this.doador = doador;
	}

	public String getPisPasep() {
		return pisPasep;
	}

	public void setPisPasep(String pisPasep) {
		this.pisPasep = pisPasep;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getDataEmissaoIdentidade() {
		return dataEmissaoIdentidade;
	}

	public void setDataEmissaoIdentidade(String dataEmissaoIdentidade) {
		this.dataEmissaoIdentidade = dataEmissaoIdentidade;
	}

	public String getTituloEleitoral() {
		return tituloEleitoral;
	}

	public void setTituloEleitoral(String tituloEleitoral) {
		this.tituloEleitoral = tituloEleitoral;
	}

	public String getZonaEleitoral() {
		return zonaEleitoral;
	}

	public void setZonaEleitoral(String zonaEleitoral) {
		this.zonaEleitoral = zonaEleitoral;
	}

	public String getSecaoEleitoral() {
		return secaoEleitoral;
	}

	public void setSecaoEleitoral(String secaoEleitoral) {
		this.secaoEleitoral = secaoEleitoral;
	}

	public String getLocalidadeEleitoral() {
		return localidadeEleitoral;
	}

	public void setLocalidadeEleitoral(String localidadeEleitoral) {
		this.localidadeEleitoral = localidadeEleitoral;
	}

	public String getEmissorIdentidade() {
		return emissorIdentidade;
	}

	public void setEmissorIdentidade(String emissorIdentidade) {
		this.emissorIdentidade = emissorIdentidade;
	}

}
