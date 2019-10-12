package br.org.crea.commons.models.commons.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParcelamentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1601970787777924551L;
	
	private int __index;
	private String codigo;
	private String numeroTermo;
	private String data;
	private Long qtdParcelas;
	private Long qtdParcelasPagas;
	private BigDecimal valorTotal;
	private String valorTotalFormatado;
	private BigDecimal valorPagar;
	private String valorPagarFormatado;
	private String descricaoDaDivida;
	private String codigoStatusDivida;
	private String codigoNaturezaDivida;
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNumeroTermo() {
		return numeroTermo;
	}
	public void setNumeroTermo(String numeroTermo) {
		this.numeroTermo = numeroTermo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Long getQtdParcelas() {
		return qtdParcelas;
	}
	public void setQtdParcelas(Long qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}
	public Long getQtdParcelasPagas() {
		return qtdParcelasPagas;
	}
	public void setQtdParcelasPagas(Long qtdParcelasPagas) {
		this.qtdParcelasPagas = qtdParcelasPagas;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getValorTotalFormatado() {
		return valorTotalFormatado;
	}
	public void setValorTotalFormatado(String valorTotalFormatado) {
		this.valorTotalFormatado = valorTotalFormatado;
	}
	public BigDecimal getValorPagar() {
		return valorPagar;
	}
	public void setValorPagar(BigDecimal valorPagar) {
		this.valorPagar = valorPagar;
	}
	public String getValorPagarFormatado() {
		return valorPagarFormatado;
	}
	public void setValorPagarFormatado(String valorPagarFormatado) {
		this.valorPagarFormatado = valorPagarFormatado;
	}
	public String getDescricaoDaDivida() {
		return descricaoDaDivida;
	}
	public void setDescricaoDaDivida(String descricaoDaDivida) {
		this.descricaoDaDivida = descricaoDaDivida;
	}
	public String getCodigoStatusDivida() {
		return codigoStatusDivida;
	}
	public void setCodigoStatusDivida(String codigoStatusDivida) {
		this.codigoStatusDivida = codigoStatusDivida;
	}
	public String getCodigoNaturezaDivida() {
		return codigoNaturezaDivida;
	}
	public void setCodigoNaturezaDivida(String codigoNaturezaDivida) {
		this.codigoNaturezaDivida = codigoNaturezaDivida;
	}
	public int get__index() {
		return __index;
	}
	public void set__index(int __index) {
		this.__index = __index;
	}
	

}
