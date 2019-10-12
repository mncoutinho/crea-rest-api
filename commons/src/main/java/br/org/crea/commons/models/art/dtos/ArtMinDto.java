package br.org.crea.commons.models.art.dtos;

import java.math.BigDecimal;

public class ArtMinDto {
	
	private String numero;
	
	private String dataCadastro;
	
	private String dataPagamento;
	
	private BigDecimal valorPago;
	
	private String natureza;
	
	private Long idBaixa;
	
	private String baixa;
	
	private String situacao;
	
	private String codigoDivida;
	
	private String statusDivida;
	
	private boolean temDivida;
	
	private boolean temTaxaDeIncorporacao;
	
	private boolean temExigencia;
	
	private boolean exigencia;
	
	private Boolean finalizada;

	private Boolean baixada;
	
	private Boolean acaoOrdinaria;
	
	private String descricaoModelo;

	public String getCodigoDivida() {
		return codigoDivida;
	}

	public void setCodigoDivida(String codigoDivida) {
		this.codigoDivida = codigoDivida;
	}

	public String getStatusDivida() {
		return statusDivida;
	}

	public void setStatusDivida(String statusDivida) {
		this.statusDivida = statusDivida;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public boolean isTemDivida() {
		return temDivida;
	}

	public void setTemDivida(boolean temDivida) {
		this.temDivida = temDivida;
	}

	public boolean isExigencia() {
		return exigencia;
	}

	public void setExigencia(boolean exigencia) {
		this.exigencia = exigencia;
	}

	public String getBaixa() {
		return baixa;
	}

	public void setBaixa(String baixa) {
		this.baixa = baixa;
	}

	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
	}

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Boolean getBaixada() {
		return baixada;
	}

	public void setBaixada(Boolean baixada) {
		this.baixada = baixada;
	}

	public Boolean getAcaoOrdinaria() {
		return acaoOrdinaria;
	}

	public void setAcaoOrdinaria(Boolean acaoOrdinaria) {
		this.acaoOrdinaria = acaoOrdinaria;
	}

	public Long getIdBaixa() {
		return idBaixa;
	}

	public void setIdBaixa(Long idBaixa) {
		this.idBaixa = idBaixa;
	}
	
	public boolean isTemExigencia() {
		return temExigencia;
	}

	public void setTemExigencia(boolean temExigencia) {
		this.temExigencia = temExigencia;
	}

	public String getDescricaoModelo() {
		return descricaoModelo;
	}

	public void setDescricaoModelo(String descricaoModelo) {
		this.descricaoModelo = descricaoModelo;
	}

	public boolean isTemTaxaDeIncorporacao() {
		return temTaxaDeIncorporacao;
	}

	public void setTemTaxaDeIncorporacao(boolean temTaxaDeIncorporacao) {
		this.temTaxaDeIncorporacao = temTaxaDeIncorporacao;
	}

}
