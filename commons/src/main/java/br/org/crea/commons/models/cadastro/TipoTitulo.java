package br.org.crea.commons.models.cadastro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAD_TIPOS_TITULOS")
public class TipoTitulo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="TIPOCONFEA")
	private String tipoCONFEA;

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

	public String getTipoCONFEA() {
		return tipoCONFEA;
	}

	public void setTipoCONFEA(String tipoCONFEA) {
		this.tipoCONFEA = tipoCONFEA;
	}

}
