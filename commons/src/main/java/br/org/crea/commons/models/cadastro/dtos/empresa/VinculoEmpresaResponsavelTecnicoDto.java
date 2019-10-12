package br.org.crea.commons.models.cadastro.dtos.empresa;

import br.org.crea.commons.models.commons.dtos.EnderecoDto;

public class VinculoEmpresaResponsavelTecnicoDto {

	private String dataInicioQt;
	
	private String dataInicioRt;
	
	private Long registroEmpresa;
	
	private String razaoSocialEmpresa;
	
	private EnderecoDto enderecoEmpresa;
	
	private String ramoAtividade;
	
	private String jornadaProfissional;
	
	private String numeroArtCargoFuncao;
	
	private String salarioProfissional;
	
	private String tipoVinculoProfissional;
	
	private boolean ehResponsavelTecnico;
	
	private String responsavelTecnico;
	
	private Long codigoQuadroTecnico;
	
	private String cnpjEmpresa;
	
	private String ArtCargoFuncao;

	public String getDataInicioQt() {
		return dataInicioQt;
	}

	public void setDataInicioQt(String dataInicioQt) {
		this.dataInicioQt = dataInicioQt;
	}

	public String getDataInicioRt() {
		return dataInicioRt;
	}

	public void setDataInicioRt(String dataInicioRt) {
		this.dataInicioRt = dataInicioRt;
	}

	public Long getRegistroEmpresa() {
		return registroEmpresa;
	}

	public void setRegistroEmpresa(Long registroEmpresa) {
		this.registroEmpresa = registroEmpresa;
	}

	public String getRazaoSocialEmpresa() {
		return razaoSocialEmpresa;
	}

	public void setRazaoSocialEmpresa(String razaoSocialEmpresa) {
		this.razaoSocialEmpresa = razaoSocialEmpresa;
	}

	public EnderecoDto getEnderecoEmpresa() {
		return enderecoEmpresa;
	}

	public void setEnderecoEmpresa(EnderecoDto enderecoEmpresa) {
		this.enderecoEmpresa = enderecoEmpresa;
	}

	public String getRamoAtividade() {
		return ramoAtividade;
	}

	public void setRamoAtividade(String ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}

	public String getJornadaProfissional() {
		return jornadaProfissional;
	}

	public void setJornadaProfissional(String jornadaProfissional) {
		this.jornadaProfissional = jornadaProfissional;
	}

	public String getNumeroArtCargoFuncao() {
		return numeroArtCargoFuncao;
	}

	public void setNumeroArtCargoFuncao(String numeroArtCargoFuncao) {
		this.numeroArtCargoFuncao = numeroArtCargoFuncao;
	}

	public String getSalarioProfissional() {
		return salarioProfissional;
	}

	public void setSalarioProfissional(String salarioProfissional) {
		this.salarioProfissional = salarioProfissional;
	}

	public String getTipoVinculoProfissional() {
		return tipoVinculoProfissional;
	}

	public void setTipoVinculoProfissional(String tipoVinculoProfissional) {
		this.tipoVinculoProfissional = tipoVinculoProfissional;
	}

	public boolean isEhResponsavelTecnico() {
		return ehResponsavelTecnico;
	}

	public void setEhResponsavelTecnico(boolean ehResponsavelTecnico) {
		this.ehResponsavelTecnico = ehResponsavelTecnico;
	}

	public String getResponsavelTecnico() {
		return responsavelTecnico;
	}

	public void setResponsavelTecnico(String responsavelTecnico) {
		this.responsavelTecnico = responsavelTecnico;
	}

	public Long getCodigoQuadroTecnico() {
		return codigoQuadroTecnico;
	}

	public void setCodigoQuadroTecnico(Long codigoQuadroTecnico) {
		this.codigoQuadroTecnico = codigoQuadroTecnico;
	}

	public String getCnpjEmpresa() {
		return cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa) {
		this.cnpjEmpresa = cnpjEmpresa;
	}

	public String getArtCargoFuncao() {
		return ArtCargoFuncao;
	}

	public void setArtCargoFuncao(String artCargoFuncao) {
		ArtCargoFuncao = artCargoFuncao;
	}

}
