package br.org.crea.commons.models.corporativo.pessoa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAD_TIPOS_EMPRESAS")
public class TipoEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="PAGA_ANUIDADE")
	private Boolean pagaAnuidade;
	
	@Column(name="POSSUI_CNPJ")
	private Boolean possuiCnpj;
	
	@Column(name="POSSUI_CAPITAL")
	private Boolean possuiCapital;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getPagaAnuidade() {
		return pagaAnuidade;
	}

	public void setPagaAnuidade(Boolean pagaAnuidade) {
		this.pagaAnuidade = pagaAnuidade;
	}

	public Boolean getPossuiCnpj() {
		return possuiCnpj;
	}

	public void setPossuiCnpj(Boolean possuiCnpj) {
		this.possuiCnpj = possuiCnpj;
	}

	public Boolean getPossuiCapital() {
		return possuiCapital;
	}

	public void setPossuiCapital(Boolean possuiCapital) {
		this.possuiCapital = possuiCapital;
	}
	
}
