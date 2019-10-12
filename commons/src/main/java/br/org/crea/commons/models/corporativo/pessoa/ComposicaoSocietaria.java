package br.org.crea.commons.models.corporativo.pessoa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_COMPOSICOES_SOCIETARIAS")
public class ComposicaoSocietaria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8809083243969048135L;

	@Id
	@Column(name = "ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_EMPRESAS")
	private Empresa empresa;
	
	@OneToOne
	@JoinColumn(name = "FK_CODIGO_PESSOAS")
	private Pessoa socio;
	
	@Column(name = "PERCENTUAL")
	private BigDecimal percentual;

	public Long getId() {
		return id;
	}

	public void setCodigo(Long id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Pessoa getSocio() {
		return socio;
	}

	public void setSocio(Pessoa socio) {
		this.socio = socio;
	}

	public BigDecimal getPercentual() {
		return percentual;
	}

	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
	}

}
