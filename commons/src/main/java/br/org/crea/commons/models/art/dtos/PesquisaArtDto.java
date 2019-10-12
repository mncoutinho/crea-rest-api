package br.org.crea.commons.models.art.dtos;

import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

public class PesquisaArtDto {
	
	private String tipoPesquisa;
	
	private TipoPessoa tipoPessoa;
	
	private Long idPessoa;
	
	private String numero;

	private String numeroArtPrincipal;
	
	private String numeroArt;
	
	private Long idNatureza;
	
	private Long idTipo;

	private Boolean rascunho;
	
	private String dataInicioContrato;

	private String dataFimContrato;
	
	private String dataInicioCadastro;

	private String dataFimCadastro;
	
	private String dataInicioPagamento;

	private String dataFimPagamento;
	
	private boolean exigencia;
	
	private String nomeContratante;
	
	private int page = 0;
	
	private int rows;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroArtPrincipal() {
		return numeroArtPrincipal;
	}

	public void setNumeroArtPrincipal(String numeroArtPrincipal) {
		this.numeroArtPrincipal = numeroArtPrincipal;
	}

	public Long getIdNatureza() {
		return idNatureza;
	}

	public void setIdNatureza(Long idNatureza) {
		this.idNatureza = idNatureza;
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public String getDataInicioContrato() {
		return dataInicioContrato;
	}

	public void setDataInicioContrato(String dataInicioContrato) {
		this.dataInicioContrato = dataInicioContrato;
	}

	public String getDataFimContrato() {
		return dataFimContrato;
	}

	public void setDataFimContrato(String dataFimContrato) {
		this.dataFimContrato = dataFimContrato;
	}
	
	public String getDataFimPagamento() {
		return dataFimPagamento;
	}

	public void setDataFimPagamento(String dataFimPagamento) {
		this.dataFimPagamento = dataFimPagamento;
	}

	public String getDataInicioCadastro() {
		return dataInicioCadastro;
	}

	public void setDataInicioCadastro(String dataInicioCadastro) {
		this.dataInicioCadastro = dataInicioCadastro;
	}

	public String getDataFimCadastro() {
		return dataFimCadastro;
	}

	public void setDataFimCadastro(String dataFimCadastro) {
		this.dataFimCadastro = dataFimCadastro;
	}

	public String getDataInicioPagamento() {
		return dataInicioPagamento;
	}

	public void setDataInicioPagamento(String dataInicioPagamento) {
		this.dataInicioPagamento = dataInicioPagamento;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public boolean ehPrimeiraConsulta() {
		return this.page == 1;
	}
	
	public boolean temNumeroART() {
		return !this.numero.equals("");
	}
	
	public boolean heProfissional() {
		return this.tipoPessoa == TipoPessoa.PROFISSIONAL;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	
	public Boolean getRascunho() {
		return rascunho;
	}

	public void setRascunho(Boolean rascunho) {
		this.rascunho = rascunho;
	}

	public boolean temIdNatureza() {
		return this.idNatureza != null;
	}

	public boolean temIdTipo() {
		return this.idTipo != null;
	}

	public boolean temRascunho() {
		return this.rascunho != null;
	}

	public boolean temNumeroARTPrincipal() {
		return !this.numeroArtPrincipal.equals("");
	}

	public boolean temDataInicioEfimContrato() {
		return !this.dataInicioContrato.equals("") && !this.dataFimContrato.equals("");
	}
	
	public boolean temDataInicioEfimCadastro() {
		return !this.dataInicioCadastro.equals("") && !this.dataFimCadastro.equals("");
	}

	public boolean temDataInicioEfimPagamento() {
		return !this.dataInicioPagamento.equals("") && !this.dataFimPagamento.equals("");
	}

	public String getTipoPesquisa() {
		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}

	public boolean isExigencia() {
		return exigencia;
	}

	public void setExigencia(boolean exigencia) {
		this.exigencia = exigencia;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public String getNomeContratante() {
		return nomeContratante;
	}

	public void setNomeContratante(String nomeContratante) {
		this.nomeContratante = nomeContratante;
	}
	
	public boolean temNomeContratante() {
		return !"".equals(this.nomeContratante);
	}
	
}
