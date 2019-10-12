package br.org.crea.commons.models.corporativo.personalidade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PER_CARGOS")
public class CargoPersonalidade{

	private Long 	id;
	private String 	descricao;
	private boolean visivelConselheiros;
	private boolean visivelInspetores;
	private boolean visivelContatos;
	private boolean visivelDirecoes;
	private boolean removido;
	private boolean permiteConsecutividade;
	private CargoPersonalidade 	cargoRaiz;
	
	@Column(name="PERMITE_CONSECUTIVIDADE")
	public boolean getPermiteConsecutividade() {
		return permiteConsecutividade;
	}
	public void setPermiteConsecutividade(boolean permiteConsecutividade) {
		this.permiteConsecutividade = permiteConsecutividade;
	}
	@Column(name="DESCRICAO")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Id
	@Column(name="CODIGO")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="REMOVIDO")
	public boolean isRemovido() {
		return removido;
	}
	public void setRemovido(boolean removido) {
		this.removido = removido;
	}
	
	@Column(name="VISIVEL_CONSELHEIROS")
	public boolean isVisivelConselheiros() {
		return visivelConselheiros;
	}
	public void setVisivelConselheiros(boolean visivelConselheiros) {
		this.visivelConselheiros = visivelConselheiros;
	}
	
	@Column(name="VISIVEL_CONTATOS")
	public boolean isVisivelContatos() {
		return visivelContatos;
	}
	public void setVisivelContatos(boolean visivelContatos) {
		this.visivelContatos = visivelContatos;
	}
	
	@Column(name="VISIVEL_DIRECOES")
	public boolean isVisivelDirecoes() {
		return visivelDirecoes;
	}
	public void setVisivelDirecoes(boolean visivelDirecoes) {
		this.visivelDirecoes = visivelDirecoes;
	}
	
	@Column(name="VISIVEL_INSPETORES")
	public boolean isVisivelInspetores() {
		return visivelInspetores;
	}
	public void setVisivelInspetores(boolean visivelInspetores) {
		this.visivelInspetores = visivelInspetores;
	}
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_CARGO")
	public CargoPersonalidade getCargoRaiz() {
		return cargoRaiz;
	}
	public void setCargoRaiz(CargoPersonalidade cargoRaiz) {
		this.cargoRaiz = cargoRaiz;
	}

}
