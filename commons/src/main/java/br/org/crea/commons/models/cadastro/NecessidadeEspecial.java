package br.org.crea.commons.models.cadastro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAD_NECESSIDADES_ESPECIAIS")
public class NecessidadeEspecial implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="CODIGO_CONFEA")
	private String codigoCONFEA;

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

	public String getCodigoCONFEA() {
		return codigoCONFEA;
	}

	public void setCodigoCONFEA(String codigoCONFEA) {
		this.codigoCONFEA = codigoCONFEA;
	}

}
