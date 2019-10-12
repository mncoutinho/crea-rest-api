package br.org.crea.commons.models.financeiro.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class DividaDto {
	
	private String id;
	
	private String tipo;
	
	private String anoDivida;
	
	private String identificadorDivida;
	
	private Integer parcela;
	
	private Long statusDivida;
	
	private String descricao;
	
	private Date dataVencimento;
	
	private String dataVencimentoFormatada;
	
	private BigDecimal valorOriginal;

	private BigDecimal valorAtual;
	
	private BigDecimal valorPago;
	
	private BigDecimal juros;
	
	private BigDecimal multa;
	
	private Integer honorarios;
	
	private Long idPessoa;
	
	private String dataPagamentoFormatada;
	
	private Date dataPagamento;
	
	private Date dataQuitacaoUltimaParcelaVencida;
	
	private String dataQuitacaoUltimaParcelaVencidaFormatada;
	
	private boolean servicoExecutado;
	
	private boolean estaEmParcelamento;
	
	private String ufPagamento;
	
	private String observacao;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getAnoDivida() {
		return anoDivida;
	}

	public void setAnoDivida(String anoDivida) {
		this.anoDivida = anoDivida;
	}

	public String getIdentificadorDivida() {
		return identificadorDivida;
	}

	public void setIdentificadorDivida(String identificadorDivida) {
		this.identificadorDivida = identificadorDivida;
	}

	public Integer getParcela() {
		return parcela;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getDataVencimentoFormatada() {
		return dataVencimentoFormatada;
	}

	public void setDataVencimentoFormatada(String dataVencimentoFormatada) {
		this.dataVencimentoFormatada = dataVencimentoFormatada;
	}

	public BigDecimal getValorOriginal() {
		return valorOriginal;
	}

	public void setValorOriginal(BigDecimal valorOriginal) {
		this.valorOriginal = valorOriginal;
	}

	public BigDecimal getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(BigDecimal valorAtual) {
		this.valorAtual = valorAtual;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Integer getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(Integer honorarios) {
		this.honorarios = honorarios;
	}

	public Long getStatusDivida() {
		return statusDivida;
	}

	public void setStatusDivida(Long statusDivida) {
		this.statusDivida = statusDivida;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getDataPagamentoFormatada() {
		return dataPagamentoFormatada;
	}

	public void setDataPagamentoFormatada(String dataPagamentoFormatada) {
		this.dataPagamentoFormatada = dataPagamentoFormatada;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataQuitacaoUltimaParcelaVencida() {
		return dataQuitacaoUltimaParcelaVencida;
	}

	public void setDataQuitacaoUltimaParcelaVencida(Date dataQuitacaoUltimaParcelaVencida) { 
		this.dataQuitacaoUltimaParcelaVencida = dataQuitacaoUltimaParcelaVencida;
	}

	public String getDataQuitacaoUltimaParcelaVencidaFormatada() {
		return dataQuitacaoUltimaParcelaVencidaFormatada;
	}

	public void setDataQuitacaoUltimaParcelaVencidaFormatada(String dataQuitacaoUltimaParcelaVencidaFormatada) {
		this.dataQuitacaoUltimaParcelaVencidaFormatada = dataQuitacaoUltimaParcelaVencidaFormatada;
	}

	public boolean isServicoExecutado() {
		return servicoExecutado;
	}

	public void setServicoExecutado(boolean servicoExecutado) {
		this.servicoExecutado = servicoExecutado;
	}

	public boolean isEstaEmParcelamento() {
		return estaEmParcelamento;
	}

	public void setEstaEmParcelamento(boolean estaEmParcelamento) {
		this.estaEmParcelamento = estaEmParcelamento;
	}

	public String getUfPagamento() {
		return ufPagamento;
	}

	public void setUfPagamento(String ufPagamento) {
		this.ufPagamento = ufPagamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}
	
}
