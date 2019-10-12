package br.org.crea.commons.models.art.dtos;

import br.org.crea.commons.models.commons.dtos.GenericDto;


public class ReceitaProdutoDto {
	
	private Long idReceita;
	
	private ArtProdutoDto produto;
	
	private Long quantidade;
	
	private GenericDto unidadeMedidaQuantidade;
	
	private Long dose;
	
	private GenericDto unidadeMedidaDose;
	
	private String concentracao;
	
	private Long intervaloAplicacao;
	
	private String periodoCarencia;
	
	private String prescricao;
	
	public Long getIdReceita() {
		return idReceita;
	}
	public void setIdReceita(Long idReceita) {
		this.idReceita = idReceita;
	}
	
	public ArtProdutoDto getProduto() {
		return produto;
	}
	public void setProduto(ArtProdutoDto produto) {
		this.produto = produto;
	}
	public Long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	public GenericDto getUnidadeMedidaQuantidade() {
		return unidadeMedidaQuantidade;
	}
	public void setUnidadeMedidaQuantidade(GenericDto unidadeMedidaQuantidade) {
		this.unidadeMedidaQuantidade = unidadeMedidaQuantidade;
	}
	public Long getDose() {
		return dose;
	}
	public void setDose(Long dose) {
		this.dose = dose;
	}
	public GenericDto getUnidadeMedidaDose() {
		return unidadeMedidaDose;
	}
	public void setUnidadeMedidaDose(GenericDto unidadeMedidaDose) {
		this.unidadeMedidaDose = unidadeMedidaDose;
	}
	public String getConcentracao() {
		return concentracao;
	}
	public void setConcentracao(String concentracao) {
		this.concentracao = concentracao;
	}
	public Long getIntervaloAplicacao() {
		return intervaloAplicacao;
	}
	public void setIntervaloAplicacao(Long intervaloAplicacao) {
		this.intervaloAplicacao = intervaloAplicacao;
	}
	public String getPeriodoCarencia() {
		return periodoCarencia;
	}
	public void setPeriodoCarencia(String periodoCarencia) {
		this.periodoCarencia = periodoCarencia;
	}
	public String getPrescricao() {
		return prescricao;
	}
	public void setPrescricao(String prescricao) {
		this.prescricao = prescricao;
	}
	
	
}
