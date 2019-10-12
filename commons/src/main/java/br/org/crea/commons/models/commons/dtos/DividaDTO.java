package br.org.crea.commons.models.commons.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class DividaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3887043327657397892L;
	
	private int __index;
	private String codigo;
	private String identificadorDivida;
	private String fase;
	private BigDecimal valorOriginal;
	private String valorOriginalFormatado;
	private BigDecimal juros;
	private String jurosFormatado;
	private BigDecimal honorarios;
	private String honorariosFormatado;
	private BigDecimal valorAtual;
	private String valorAtualFormatado;
	private BigDecimal multa;
	private String multaFormatada;
	private String dataVencimento;
	private BigDecimal valorReajustado;
	private String valorReajustadoFormatado;
	private String parcela;
	private BigDecimal valorPago;
	private String valorPagoFormatado;
	private String descricaoNatureza;
	private String descricaoStatusDaDivida;
	private Long codigoStatusDaDivida;
	private Long statusDividaQuitado;
	private boolean dividaVencida;
	
	public int get__index() {
		return __index;
	}
	public void set__index(int __index) {
		this.__index = __index;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getIdentificadorDivida() {
		return identificadorDivida;
	}
	public void setIdentificadorDivida(String identificadorDivida) {
		this.identificadorDivida = identificadorDivida;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public BigDecimal getValorOriginal() {
		return valorOriginal;
	}
	public void setValorOriginal(BigDecimal valorOriginal) {
		this.valorOriginal = valorOriginal;
	}
	public String getValorOriginalFormatado() {
		return valorOriginalFormatado;
	}
	public void setValorOriginalFormatado(String valorOriginalFormatado) {
		this.valorOriginalFormatado = valorOriginalFormatado;
	}
	public BigDecimal getJuros() {
		return juros;
	}
	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}
	public String getJurosFormatado() {
		return jurosFormatado;
	}
	public void setJurosFormatado(String jurosFormatado) {
		this.jurosFormatado = jurosFormatado;
	}
	public BigDecimal getHonorarios() {
		return honorarios;
	}
	public void setHonorarios(BigDecimal honorarios) {
		this.honorarios = honorarios;
	}
	public String getHonorariosFormatado() {
		return honorariosFormatado;
	}
	public void setHonorariosFormatado(String honorariosFormatado) {
		this.honorariosFormatado = honorariosFormatado;
	}
	public BigDecimal getValorAtual() {
		return valorAtual;
	}
	public void setValorAtual(BigDecimal valorAtual) {
		this.valorAtual = valorAtual;
	}
	public String getValorAtualFormatado() {
		return valorAtualFormatado;
	}
	public void setValorAtualFormatado(String valorAtualFormatado) {
		this.valorAtualFormatado = valorAtualFormatado;
	}
	public BigDecimal getMulta() {
		return multa;
	}
	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}
	public String getMultaFormatada() {
		return multaFormatada;
	}
	public void setMultaFormatada(String multaFormatada) {
		this.multaFormatada = multaFormatada;
	}
	public String getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public BigDecimal getValorReajustado() {
		return valorReajustado;
	}
	public void setValorReajustado(BigDecimal valorReajustado) {
		this.valorReajustado = valorReajustado;
	}
	public String getValorReajustadoFormatado() {
		return valorReajustadoFormatado;
	}
	public void setValorReajustadoFormatado(String valorReajustadoFormatado) {
		this.valorReajustadoFormatado = valorReajustadoFormatado;
	}
	public String getParcela() {
		return parcela;
	}
	public void setParcela(String parcela) {
		this.parcela = parcela;
	}
	public BigDecimal getValorPago() {
		return valorPago;
	}
	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
	public String getValorPagoFormatado() {
		return valorPagoFormatado;
	}
	public void setValorPagoFormatado(String valorPagoFormatado) {
		this.valorPagoFormatado = valorPagoFormatado;
	}
	public String getDescricaoNatureza() {
		return descricaoNatureza;
	}
	public void setDescricaoNatureza(String descricaoNatureza) {
		this.descricaoNatureza = descricaoNatureza;
	}
	public String getDescricaoStatusDaDivida() {
		return descricaoStatusDaDivida;
	}
	public void setDescricaoStatusDaDivida(String descricaoStatusDaDivida) {
		this.descricaoStatusDaDivida = descricaoStatusDaDivida;
	}
	public Long getCodigoStatusDaDivida() {
		return codigoStatusDaDivida;
	}
	public void setCodigoStatusDaDivida(Long codigoStatusDaDivida) {
		this.codigoStatusDaDivida = codigoStatusDaDivida;
	}
	public Long getStatusDividaQuitado() {
		return statusDividaQuitado;
	}
	public void setStatusDividaQuitado(Long statusDividaQuitado) {
		this.statusDividaQuitado = statusDividaQuitado;
	}
	public boolean isDividaVencida() {
		return dividaVencida;
	}
	public void setDividaVencida(boolean dividaVencida) {
		this.dividaVencida = dividaVencida;
	}
	
	
}
