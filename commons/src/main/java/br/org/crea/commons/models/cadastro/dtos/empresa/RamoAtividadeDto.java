package br.org.crea.commons.models.cadastro.dtos.empresa;

import java.io.Serializable;

public class RamoAtividadeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private String ramo;

	private String atividade;

	private String dataInclusao;

	private String dataAprovacao;

	private String dataCancelamento;
	
	private String possuiResponsavelTecnico;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getRamo() {
		return ramo;
	}

	public void setRamo(String ramo) {
		this.ramo = ramo;
	}

	public String getAtividade() {
		return atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(String dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(String dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public String getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(String dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getPossuiResponsavelTecnico() {
		return possuiResponsavelTecnico;
	}

	public void setPossuiResponsavelTecnico(String possuiResponsavelTecnico) {
		this.possuiResponsavelTecnico = possuiResponsavelTecnico;
	}
	
}
