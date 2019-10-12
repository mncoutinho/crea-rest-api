package br.org.crea.commons.models.art;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ART_CUB")
public class ArtCUB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6489075984924410868L;

	@Id
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="MES")
	private Long mes;
	
	@Column(name="ANO")
	private Long ano;
	
	@Column(name="VALOR")
	private BigDecimal valor;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
}
