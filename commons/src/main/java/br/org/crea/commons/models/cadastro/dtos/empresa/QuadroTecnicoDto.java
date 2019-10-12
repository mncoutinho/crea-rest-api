package br.org.crea.commons.models.cadastro.dtos.empresa;

import java.util.List;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;


public class QuadroTecnicoDto {
	
	private Long id;
	
	private PessoaDto empresa;

	private PessoaDto profissional;
	
	private String dataInicioQuadro;
	
	private String dataAdmissao;
	
	private String dataDesligamento;
	
	private String dataContrato;
	
	private String dataValidadeContrato;
	
	private String dataInicioResponsavel;
	
	private String dataFimResponsavel;
	
	private String dataAprovacao;

	private String dataFimQuadro;
	
	private Boolean ehResponsavelTecnico;
	
	private Boolean baixado;
	
	private Boolean possuiDivida;
	
	private String jornadaTrabalho;
	
	private DomainGenericDto vinculo;
	
	private String remuneracao;
	
	private String ramoDescricao;
	
	private String atividadeRamoDescricao;
	
	private List<ResponsavelTecnicoDto> responsaveis;
	
	private boolean ehVisaoProfissional;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PessoaDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaDto empresa) {
		this.empresa = empresa;
	}

	public PessoaDto getProfissional() {
		return profissional;
	}

	public void setProfissional(PessoaDto profissional) {
		this.profissional = profissional;
	}

	public String getDataInicioQuadro() {
		return dataInicioQuadro;
	}

	public void setDataInicioQuadro(String dataInicioQuadro) {
		this.dataInicioQuadro = dataInicioQuadro;
	}

	public String getDataFimQuadro() {
		return dataFimQuadro;
	}

	public void setDataFimQuadro(String dataFimQuadro) {
		this.dataFimQuadro = dataFimQuadro;
	}

	public List<ResponsavelTecnicoDto> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<ResponsavelTecnicoDto> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public Boolean getEhResponsavelTecnico() {
		return ehResponsavelTecnico;
	}

	public void setEhResponsavelTecnico(Boolean ehResponsavelTecnico) {
		this.ehResponsavelTecnico = ehResponsavelTecnico;
	}

	public Boolean getPossuiDivida() {
		return possuiDivida;
	}

	public void setPossuiDivida(Boolean possuiDivida) {
		this.possuiDivida = possuiDivida;
	}

	public Boolean getBaixado() {
		return baixado;
	}

	public void setBaixado(Boolean baixado) {
		this.baixado = baixado;
	}

	public String getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(String dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public String getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(String dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}

	public String getDataContrato() {
		return dataContrato;
	}

	public void setDataContrato(String dataContrato) {
		this.dataContrato = dataContrato;
	}

	public String getDataValidadeContrato() {
		return dataValidadeContrato;
	}

	public void setDataValidadeContrato(String dataValidadeContrato) {
		this.dataValidadeContrato = dataValidadeContrato;
	}

	public String getDataInicioResponsavel() {
		return dataInicioResponsavel;
	}

	public void setDataInicioResponsavel(String dataInicioResponsavel) {
		this.dataInicioResponsavel = dataInicioResponsavel;
	}

	public String getDataFimResponsavel() {
		return dataFimResponsavel;
	}

	public void setDataFimResponsavel(String dataFimResponsavel) {
		this.dataFimResponsavel = dataFimResponsavel;
	}

	public String getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(String dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
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

	public String getRemuneracao() {
		return remuneracao;
	}

	public void setRemuneracao(String remuneracao) {
		this.remuneracao = remuneracao;
	}

	public String getRamoDescricao() {
		return ramoDescricao;
	}

	public void setRamoDescricao(String ramoDescricao) {
		this.ramoDescricao = ramoDescricao;
	}

	public String getAtividadeRamoDescricao() {
		return atividadeRamoDescricao;
	}

	public void setAtividadeRamoDescricao(String atividadeRamoDescricao) {
		this.atividadeRamoDescricao = atividadeRamoDescricao;
	}

	public boolean isEhVisaoProfissional() {
		return ehVisaoProfissional;
	}

	public void setEhVisaoProfissional(boolean ehVisaoProfissional) {
		this.ehVisaoProfissional = ehVisaoProfissional;
	}

}
