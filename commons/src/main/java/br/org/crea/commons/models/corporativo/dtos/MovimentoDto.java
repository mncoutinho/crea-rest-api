package br.org.crea.commons.models.corporativo.dtos;

import java.util.Date;

import br.org.crea.commons.models.cadastro.Departamento;

public class MovimentoDto {
	
private Long id;
	
	private Departamento departamentoOrigem;
	
	private Departamento departamentoDestino;
	
	private Date dataEnvio;
	
	private Date dataRecebimento;
	
	private Departamento situacao;
	
	private String dataEnvioFormatada;
	
	private String dataRecebimentoFormatada;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Departamento getDepartamentoOrigem() {
		return departamentoOrigem;
	}

	public void setDepartamentoOrigem(Departamento departamentoOrigem) {
		this.departamentoOrigem = departamentoOrigem;
	}

	public Departamento getDepartamentoDestino() {
		return departamentoDestino;
	}

	public void setDepartamentoDestino(Departamento departamentoDestino) {
		this.departamentoDestino = departamentoDestino;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Departamento getSituacao() {
		return situacao;
	}

	public void setSituacao(Departamento situacao) {
		this.situacao = situacao;
	}

	public String getDataEnvioFormatada() {
		return dataEnvioFormatada;
	}

	public void setDataEnvioFormatada(String dataEnvioFormatada) {
		this.dataEnvioFormatada = dataEnvioFormatada;
	}

	public String getDataRecebimentoFormatada() {
		return dataRecebimentoFormatada;
	}

	public void setDataRecebimentoFormatada(String dataRecebimentoFormatada) {
		this.dataRecebimentoFormatada = dataRecebimentoFormatada;
	}



	
}
