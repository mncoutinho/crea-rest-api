package br.org.crea.commons.models.cadastro.dtos.profissional;

import java.io.Serializable;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;

public class QuadroTecnicoProfissionalDto implements Serializable {

	private static final long serialVersionUID = -816328907542401435L;

	private Long idEmpresa;
	
	private String empresaNome;
	
	private String dataInicio;
	
	private String dataFim;
	
	private Long idProfissional;
	
	private Long idQuadroTecnico;
	
	private Long idResponsavelTecnico;

	private String responsavelTecnico;
	
	private String jornadaTrabalho;
	
	private DomainGenericDto vinculo;
	
	public Long getIdQuadroTecnico() {
		return idQuadroTecnico;
	}

	public void setIdQuadroTecnico(Long idQuadroTecnico) {
		this.idQuadroTecnico = idQuadroTecnico;
	}
	
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getEmpresaNome() {
		return empresaNome;
	}

	public void setEmpresaNome(String empresaNome) {
		this.empresaNome = empresaNome;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getResponsavelTecnico() {
		return responsavelTecnico;
	}

	public void setResponsavelTecnico(String responsavelTecnico) {
		this.responsavelTecnico = responsavelTecnico;
	}

	public String getJornadaTrabalho() {
		return jornadaTrabalho;
	}

	public void setJornadaTrabalho(String jornadaTrabalho) {
		this.jornadaTrabalho = jornadaTrabalho;
	}

	public DomainGenericDto getVinculo() {
		return vinculo;
	}

	public void setVinculo(DomainGenericDto vinculo) {
		this.vinculo = vinculo;
	}

	public Long getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(Long idProfissional) {
		this.idProfissional = idProfissional;
	}

	public Long getIdResponsavelTecnico() {
		return idResponsavelTecnico;
	}

	public void setIdResponsavelTecnico(Long idResponsavelTecnico) {
		this.idResponsavelTecnico = idResponsavelTecnico;
	}

}
