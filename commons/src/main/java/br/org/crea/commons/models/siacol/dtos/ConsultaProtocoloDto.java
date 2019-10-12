package br.org.crea.commons.models.siacol.dtos;

import java.util.Date;
import java.util.List;

import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.SituacaoProtocoloDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;


public class ConsultaProtocoloDto {
	
	
	private String tipoConsulta;
	
	private Long idPessoa;
	
	private Long idDepartamento;
	
    private Long numeroProtocolo;
	
	private Long numeroProcesso;
	
	private PessoaDto interessado;
	
	private List<PessoaDto> responsaveis;
    
    private List<DepartamentoDto> departamentos;
    
    private List<AssuntoDto> assuntos;
    
    private List<AssuntoDto> assuntosProtocolo;
    
    private List<SituacaoProtocoloDto> situacoes;
    
    private List<GenericDto> status;
    
    private List<GenericDto> classificacao;
   
	private Date inicioData;
	
	private Date fimData;
	
	private int page = 0;
	
	private int totalRows;
	
	private String ordenacao;
	
	private boolean inativo;
	
	public String getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Long getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(Long numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public PessoaDto getInteressado() {
		return interessado;
	}

	public void setInteressado(PessoaDto interessado) {
		this.interessado = interessado;
	}


	public List<DepartamentoDto> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<DepartamentoDto> departamentos) {
		this.departamentos = departamentos;
	}

	public List<AssuntoDto> getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(List<AssuntoDto> assuntos) {
		this.assuntos = assuntos;
	}

	public List<AssuntoDto> getAssuntosProtocolo() {
		return assuntosProtocolo;
	}

	public void setAssuntosProtocolo(List<AssuntoDto> assuntosProtocolo) {
		this.assuntosProtocolo = assuntosProtocolo;
	}

	public List<SituacaoProtocoloDto> getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(List<SituacaoProtocoloDto> situacoes) {
		this.situacoes = situacoes;
	}

	public List<GenericDto> getStatus() {
		return status;
	}

	public void setStatus(List<GenericDto> status) {
		this.status = status;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public List<GenericDto> getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(List<GenericDto> classificacao) {
		this.classificacao = classificacao;
	}

	public Date getInicioData() {
		return inicioData;
	}

	public void setInicioData(Date inicioData) {
		this.inicioData = inicioData;
	}

	public Date getFimData() {
		return fimData;
	}

	public void setFimData(Date fimData) {
		this.fimData = fimData;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}


	public List<PessoaDto> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<PessoaDto> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public boolean ehPrimeiraConsulta() {
		return this.page == 0 ? true : false;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public String getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(String ordenacao) {
		this.ordenacao = ordenacao;
	}

	public boolean isInativo() {
		return inativo;
	}

	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}		

}
