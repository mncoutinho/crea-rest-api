package br.org.crea.commons.models.corporativo.personalidade.dto;

import br.org.crea.commons.models.corporativo.personalidade.entity.CargoPersonalidade;

public class CargoPersonalidadeDto {
	
	private Long id;
	private String descricao;
	private boolean visivelConselheiros;
	private boolean visivelInspetores;
	private boolean visivelContatos;
	private boolean visivelDirecoes;
	private boolean removido;
	private Boolean permiteConsecutividade;
	private CargoPersonalidade cargoRaiz;
	private Long idCargoPersonalidade;
	
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
	public boolean isVisivelConselheiros() {
		return visivelConselheiros;
	}
	public void setVisivelConselheiros(boolean visivelConselheiros) {
		this.visivelConselheiros = visivelConselheiros;
	}
	public boolean isVisivelInspetores() {
		return visivelInspetores;
	}
	public void setVisivelInspetores(boolean visivelInspetores) {
		this.visivelInspetores = visivelInspetores;
	}
	public boolean isVisivelContatos() {
		return visivelContatos;
	}
	public void setVisivelContatos(boolean visivelContatos) {
		this.visivelContatos = visivelContatos;
	}
	public boolean isVisivelDirecoes() {
		return visivelDirecoes;
	}
	public void setVisivelDirecoes(boolean visivelDirecoes) {
		this.visivelDirecoes = visivelDirecoes;
	}
	public boolean isRemovido() {
		return removido;
	}
	public void setRemovido(boolean removido) {
		this.removido = removido;
	}
	public Boolean getPermiteConsecutividade() {
		return permiteConsecutividade;
	}
	public void setPermiteConsecutividade(Boolean permiteConsecutividade) {
		this.permiteConsecutividade = permiteConsecutividade;
	}
	public CargoPersonalidade getCargoRaiz() {
		return cargoRaiz;
	}
	public void setCargoRaiz(CargoPersonalidade cargoRaiz) {
		this.cargoRaiz = cargoRaiz;
	}
	public Long getIdCargoPersonalidade() {
		return idCargoPersonalidade;
	}
	public void setIdCargoPersonalidade(Long idCargoPersonalidade) {
		this.idCargoPersonalidade = idCargoPersonalidade;
	}
	
}
