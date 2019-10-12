package br.org.crea.commons.models.corporativo.personalidade.dto;

import java.math.BigDecimal;

import br.org.crea.commons.models.commons.Localidade;

public class AjudaCustoDto {
	
	private Long id;
	private String descricao;
	private boolean removido;
	private Localidade localidade;
	private Long idLocalidadeDto;
	private Integer distancia;
	private String valor;
	private BigDecimal diaria;
	private BigDecimal jeton;
	
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
	public boolean isRemovido() {
		return removido;
	}
	public void setRemovido(boolean removido) {
		this.removido = removido;
	}
	public Localidade getLocalidade() {
		return localidade;
	}
	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}
	public Long getIdLocalidadeDto() {
		return idLocalidadeDto;
	}
	public void setIdLocalidadeDto(Long idLocalidadeDto) {
		this.idLocalidadeDto = idLocalidadeDto;
	}
	public Integer getDistancia() {
		return distancia;
	}
	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public BigDecimal getDiaria() {
		return diaria;
	}
	public void setDiaria(BigDecimal diaria) {
		this.diaria = diaria;
	}
	public BigDecimal getJeton() {
		return jeton;
	}
	public void setJeton(BigDecimal jeton) {
		this.jeton = jeton;
	}
	
	
	
	
}
