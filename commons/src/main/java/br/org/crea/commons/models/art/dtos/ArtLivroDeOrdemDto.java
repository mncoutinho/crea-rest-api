package br.org.crea.commons.models.art.dtos;

import br.org.crea.commons.models.commons.dtos.ArquivoDto;

public class ArtLivroDeOrdemDto {
	
	private String codigo;
	
	private String numeroArt;
	
	private String dataInicioDaEtapa;
	
	private String dataInicioDaEtapaFormatada;
	
	private String dataInicioDaEtapaFormatadaInput;
	
	private String dataConclusao;
	
	private String dataConclusaoFormatada;
	
	private String dataConclusaoFormatadaInput;
	
	private String relatoVisitaResponsavelTecnico;
	
	private String orientacao;
	
	private String acidentesDanosMateriais;
	
	private String empresasePrestadoresContratadosSubContratados;
	
	private String periodosInterrupcaoEMotivos;
	
	private String outrosFatosEObservacoes;
	
	private ArquivoDto arquivo;
	
	private String idPessoa;
	
	private String cpfCnpjContratado;
	
	private String nomeContratado;
	
	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public String getDataInicioDaEtapa() {
		return dataInicioDaEtapa;
	}

	public void setDataInicioDaEtapa(String dataInicioDaEtapa) {
		this.dataInicioDaEtapa = dataInicioDaEtapa;
	}

	public String getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(String dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public String getRelatoVisitaResponsavelTecnico() {
		return relatoVisitaResponsavelTecnico;
	}

	public void setRelatoVisitaResponsavelTecnico(String relatoVisitaResponsavelTecnico) {
		this.relatoVisitaResponsavelTecnico = relatoVisitaResponsavelTecnico;
	}

	public String getOrientacao() {
		return orientacao;
	}

	public void setOrientacao(String orientacao) {
		this.orientacao = orientacao;
	}

	public String getAcidentesDanosMateriais() {
		return acidentesDanosMateriais;
	}

	public void setAcidentesDanosMateriais(String acidentesDanosMateriais) {
		this.acidentesDanosMateriais = acidentesDanosMateriais;
	}

	public String getEmpresasePrestadoresContratadosSubContratados() {
		return empresasePrestadoresContratadosSubContratados;
	}

	public void setEmpresasePrestadoresContratadosSubContratados(String empresasePrestadoresContratadosSubContratados) {
		this.empresasePrestadoresContratadosSubContratados = empresasePrestadoresContratadosSubContratados;
	}

	public String getPeriodosInterrupcaoEMotivos() {
		return periodosInterrupcaoEMotivos;
	}

	public void setPeriodosInterrupcaoEMotivos(String periodosInterrupcaoEMotivos) {
		this.periodosInterrupcaoEMotivos = periodosInterrupcaoEMotivos;
	}

	public String getOutrosFatosEObservacoes() {
		return outrosFatosEObservacoes;
	}

	public void setOutrosFatosEObservacoes(String outrosFatosEObservacoes) {
		this.outrosFatosEObservacoes = outrosFatosEObservacoes;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDataConclusaoFormatada() {
		return dataConclusaoFormatada;
	}

	public void setDataConclusaoFormatada(String dataConclusaoFormatada) {
		this.dataConclusaoFormatada = dataConclusaoFormatada;
	}

	public String getDataInicioDaEtapaFormatada() {
		return dataInicioDaEtapaFormatada;
	}

	public void setDataInicioDaEtapaFormatada(String dataInicioDaEtapaFormatada) {
		this.dataInicioDaEtapaFormatada = dataInicioDaEtapaFormatada;
	}
	
	public boolean temCodigo() {
		return this.codigo != null;
	}

	public ArquivoDto getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoDto arquivo) {
		this.arquivo = arquivo;
	}

	public String getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getCpfCnpjContratado() {
		return cpfCnpjContratado;
	}

	public void setCpfCnpjContratado(String cpfCnpjContratado) {
		this.cpfCnpjContratado = cpfCnpjContratado;
	}

	public String getNomeContratado() {
		return nomeContratado;
	}

	public void setNomeContratado(String nomeContratado) {
		this.nomeContratado = nomeContratado;
	}

	public boolean temArquivo() {
		return this.arquivo != null;
	}

	public boolean temPessoa() {
		return this.idPessoa != null;
	}

	public boolean temIdArquivo() {
		if (temArquivo()) {
			return this.arquivo.getId() != null;
		}
		return false;
	}

	public String getDataInicioDaEtapaFormatadaInput() {
		return dataInicioDaEtapaFormatadaInput;
	}

	public void setDataInicioDaEtapaFormatadaInput(String dataInicioDaEtapaFormatadaInput) {
		this.dataInicioDaEtapaFormatadaInput = dataInicioDaEtapaFormatadaInput;
	}

	public String getDataConclusaoFormatadaInput() {
		return dataConclusaoFormatadaInput;
	}

	public void setDataConclusaoFormatadaInput(String dataConclusaoFormatadaInput) {
		this.dataConclusaoFormatadaInput = dataConclusaoFormatadaInput;
	}
	
}
