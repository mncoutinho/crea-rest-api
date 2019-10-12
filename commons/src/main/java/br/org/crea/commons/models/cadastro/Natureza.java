package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIN_NATUREZA")
public class Natureza {

	@Id
	@Column(name="CODIGO")
	private Long id;

	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="ATIVO")
	private Boolean ativo;
	
	@Column(name="PARA_PROFISSIONAIS")
	private Boolean profissionais;
	
	@Column(name="PARA_EMPRESAS")
	private Boolean empresas;
	
	@Column(name="PARA_LEIGOSS")
	private Boolean leigos;
	
	@Column(name="PARA_CANCELADO")
	private Boolean cancelados;
	
	@Column(name="DEVOLUCAO")
	private Boolean devolucao;

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

	public Boolean getDevolucao() {
		return devolucao;
	}

	public void setDevolucao(Boolean devolucao) {
		this.devolucao = devolucao;
	}
	
	
	
	


	

}
