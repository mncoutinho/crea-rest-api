package br.org.crea.commons.models.corporativo.pessoa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAD_TIPOS_GRUPOS")
public class TipoGrupo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="DESCRICAO")
	private String descricao;

	private BigDecimal divisor;
	
	private BigDecimal rpj;

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

	public BigDecimal getDivisor() {
		return divisor;
	}

	public void setDivisor(BigDecimal divisor) {
		this.divisor = divisor;
	}

	public BigDecimal getRpj() {
		return rpj;
	}

	public void setRpj(BigDecimal rpj) {
		this.rpj = rpj;
	}
	
}
