package br.org.crea.commons.models.commons.dtos;

import java.math.BigDecimal;
import java.util.List;

public class ParcelamentoResponseDTO {
	
	private boolean sucess;
	private String mensagem;
	private boolean funcFinanceiro;
	private List<DividaDTO> listaDividasAnuidades;
	private List<DividaDTO> listaDividasAnuidadesSelecionadas;
	private List<DividaDTO> listaDividasTermoDeParcelamento;
	private String tipoParcelamento;
	private String codigoPessoa;
	private String totalValorAtualDivAnuid;
	private String numeroMaximoQuotas;
	private String dataVencimento;
	private String quota;
	private String numeroTermo;
	private List<DividaDTO> listaDividaTaxaScpc;
	private DividaDTO primeiraDividaCadastradaAnuid;
	private String qtdeParcelasInstrucaoBoleto;
	private List<DividaDTO> listaDividasParcelasCadastradas;
	private List<DividaDTO> listaDividasGeracaoBoletoAnuid;
	private BigDecimal totalValorAtualTaxaScpc;
	private String totalValorAtualTaxaScpcFormatado;
	private BigDecimal totalValorAtualParcAnuid;
	private String totalValorAtualParcAnuidFormatado;
	private String idParcelamento;
	private List<ParcelamentoDTO> listaTermosParcelamento;
	private Long menorParcelaEmAberto;
	private String menorExercicioAberto;
	private BigDecimal totalValorAtualDividas;
	private String codigoDivida;
	
	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public boolean isFuncFinanceiro() {
		return funcFinanceiro;
	}
	public void setFuncFinanceiro(boolean funcFinanceiro) {
		this.funcFinanceiro = funcFinanceiro;
	}
	public List<DividaDTO> getListaDividasAnuidades() {
		return listaDividasAnuidades;
	}
	public void setListaDividasAnuidades(List<DividaDTO> listaDividasAnuidades) {
		this.listaDividasAnuidades = listaDividasAnuidades;
	}
	public String getTipoParcelamento() {
		return tipoParcelamento;
	}
	public void setTipoParcelamento(String tipoParcelamento) {
		this.tipoParcelamento = tipoParcelamento;
	}
	public String getCodigoPessoa() {
		return codigoPessoa;
	}
	public void setCodigoPessoa(String codigoPessoa) {
		this.codigoPessoa = codigoPessoa;
	}
	public String getTotalValorAtualDivAnuid() {
		return totalValorAtualDivAnuid;
	}
	public void setTotalValorAtualDivAnuid(String totalValorAtualDivAnuid) {
		this.totalValorAtualDivAnuid = totalValorAtualDivAnuid;
	}
	public String getNumeroMaximoQuotas() {
		return numeroMaximoQuotas;
	}
	public void setNumeroMaximoQuotas(String numeroMaximoQuotas) {
		this.numeroMaximoQuotas = numeroMaximoQuotas;
	}
	public String getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public String getNumeroTermo() {
		return numeroTermo;
	}
	public void setNumeroTermo(String numeroTermo) {
		this.numeroTermo = numeroTermo;
	}
	public List<DividaDTO> getListaDividaTaxaScpc() {
		return listaDividaTaxaScpc;
	}
	public void setListaDividaTaxaScpc(List<DividaDTO> listaDividaTaxaScpc) {
		this.listaDividaTaxaScpc = listaDividaTaxaScpc;
	}
	public DividaDTO getPrimeiraDividaCadastradaAnuid() {
		return primeiraDividaCadastradaAnuid;
	}
	public void setPrimeiraDividaCadastradaAnuid(
			DividaDTO primeiraDividaCadastradaAnuid) {
		this.primeiraDividaCadastradaAnuid = primeiraDividaCadastradaAnuid;
	}
	public String getQtdeParcelasInstrucaoBoleto() {
		return qtdeParcelasInstrucaoBoleto;
	}
	public void setQtdeParcelasInstrucaoBoleto(String qtdeParcelasInstrucaoBoleto) {
		this.qtdeParcelasInstrucaoBoleto = qtdeParcelasInstrucaoBoleto;
	}
	public List<DividaDTO> getListaDividasParcelasCadastradas() {
		return listaDividasParcelasCadastradas;
	}
	public void setListaDividasParcelasCadastradas(
			List<DividaDTO> listaDividasParcelasCadastradas) {
		this.listaDividasParcelasCadastradas = listaDividasParcelasCadastradas;
	}
	public List<DividaDTO> getListaDividasGeracaoBoletoAnuid() {
		return listaDividasGeracaoBoletoAnuid;
	}
	public void setListaDividasGeracaoBoletoAnuid(
			List<DividaDTO> listaDividasGeracaoBoletoAnuid) {
		this.listaDividasGeracaoBoletoAnuid = listaDividasGeracaoBoletoAnuid;
	}
	public BigDecimal getTotalValorAtualTaxaScpc() {
		return totalValorAtualTaxaScpc;
	}
	public void setTotalValorAtualTaxaScpc(BigDecimal totalValorAtualTaxaScpc) {
		this.totalValorAtualTaxaScpc = totalValorAtualTaxaScpc;
	}
	public String getTotalValorAtualTaxaScpcFormatado() {
		return totalValorAtualTaxaScpcFormatado;
	}
	public void setTotalValorAtualTaxaScpcFormatado(
			String totalValorAtualTaxaScpcFormatado) {
		this.totalValorAtualTaxaScpcFormatado = totalValorAtualTaxaScpcFormatado;
	}
	public List<DividaDTO> getListaDividasAnuidadesSelecionadas() {
		return listaDividasAnuidadesSelecionadas;
	}
	public void setListaDividasAnuidadesSelecionadas(
			List<DividaDTO> listaDividasAnuidadesSelecionadas) {
		this.listaDividasAnuidadesSelecionadas = listaDividasAnuidadesSelecionadas;
	}
	public BigDecimal getTotalValorAtualParcAnuid() {
		return totalValorAtualParcAnuid;
	}
	public void setTotalValorAtualParcAnuid(BigDecimal totalValorAtualParcAnuid) {
		this.totalValorAtualParcAnuid = totalValorAtualParcAnuid;
	}
	public String getTotalValorAtualParcAnuidFormatado() {
		return totalValorAtualParcAnuidFormatado;
	}
	public void setTotalValorAtualParcAnuidFormatado(
			String totalValorAtualParcAnuidFormatado) {
		this.totalValorAtualParcAnuidFormatado = totalValorAtualParcAnuidFormatado;
	}
	public String getIdParcelamento() {
		return idParcelamento;
	}
	public void setIdParcelamento(String idParcelamento) {
		this.idParcelamento = idParcelamento;
	}
	public List<ParcelamentoDTO> getListaTermosParcelamento() {
		return listaTermosParcelamento;
	}
	public void setListaTermosParcelamento(
			List<ParcelamentoDTO> listaTermosParcelamento) {
		this.listaTermosParcelamento = listaTermosParcelamento;
	}
	public Long getMenorParcelaEmAberto() {
		return menorParcelaEmAberto;
	}
	public void setMenorParcelaEmAberto(Long menorParcelaEmAberto) {
		this.menorParcelaEmAberto = menorParcelaEmAberto;
	}
	public String getMenorExercicioAberto() {
		return menorExercicioAberto;
	}
	public void setMenorExercicioAberto(String menorExercicioAberto) {
		this.menorExercicioAberto = menorExercicioAberto;
	}
	public BigDecimal getTotalValorAtualDividas() {
		return totalValorAtualDividas;
	}
	public void setTotalValorAtualDividas(BigDecimal totalValorAtualDividas) {
		this.totalValorAtualDividas = totalValorAtualDividas;
	}
	public List<DividaDTO> getListaDividasTermoDeParcelamento() {
		return listaDividasTermoDeParcelamento;
	}
	public void setListaDividasTermoDeParcelamento(
			List<DividaDTO> listaDividasTermoDeParcelamento) {
		this.listaDividasTermoDeParcelamento = listaDividasTermoDeParcelamento;
	}
	public String getCodigoDivida() {
		return codigoDivida;
	}
	public void setCodigoDivida(String codigoDivida) {
		this.codigoDivida = codigoDivida;
	}
	
}
