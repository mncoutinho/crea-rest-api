package br.org.crea.commons.models.cadastro.dtos;

import java.math.BigDecimal;

public class TaxaDto {
	
	private Long id;
	
	private String descricao;
	
	private BigDecimal valor;
	
	private Boolean ativo;
	
	private Boolean profissionais;
	
	private Boolean empresas;
	
	private Boolean leigos;
	
	private Boolean cancelados;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getProfissionais() {
		return profissionais;
	}

	public void setProfissionais(Boolean profissionais) {
		this.profissionais = profissionais;
	}

	public Boolean getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Boolean empresas) {
		this.empresas = empresas;
	}

	public Boolean getLeigos() {
		return leigos;
	}

	public void setLeigos(Boolean leigos) {
		this.leigos = leigos;
	}

	public Boolean getCancelados() {
		return cancelados;
	}

	public void setCancelados(Boolean cancelados) {
		this.cancelados = cancelados;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	

}
