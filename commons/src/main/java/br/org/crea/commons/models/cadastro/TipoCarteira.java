package br.org.crea.commons.models.cadastro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAD_TIPOS_CARTEIRAS")
public class TipoCarteira implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	private Long codigo;

	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="DESCTIPOCARTEIRA")
	private String descricaoTipoCarteira;
	
	@Column(name="TIPOCONFEA")
	private String tipoCONFEA;
	
	@Column(name="DESCRICAOCONFEA")
	private String descricaoCONFEA;

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

	public String getDescricaoTipoCarteira() {
		return descricaoTipoCarteira;
	}

	public void setDescricaoTipoCarteira(String descricaoTipoCarteira) {
		this.descricaoTipoCarteira = descricaoTipoCarteira;
	}

	public String getTipoCONFEA() {
		return tipoCONFEA;
	}

	public void setTipoCONFEA(String tipoCONFEA) {
		this.tipoCONFEA = tipoCONFEA;
	}

	public String getDescricaoCONFEA() {
		return descricaoCONFEA;
	}

	public void setDescricaoCONFEA(String descricaoCONFEA) {
		this.descricaoCONFEA = descricaoCONFEA;
	}

}
