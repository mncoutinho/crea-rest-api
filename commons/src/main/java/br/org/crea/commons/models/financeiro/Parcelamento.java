package br.org.crea.commons.models.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;


@Entity
@Table(name="FIN_PARCELAMENTO")
public class Parcelamento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_PESSOA")
	private Pessoa pessoa;
	
	@Column(name="DATA", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(name="QT_PARCELAS")
	private Long qtdParcelas;
	
	@Column(name="QT_PARCELAS_PAGAS")
	private Long qtdParcelasPagas;
	
	@Column(name="NUMERO_TERMO")
	private Long numeroTermo;
	
	@Column(name="VALOR_TOTAL")
	private BigDecimal valorTotal;
	
	@Column(name="JUROS_TOTAL")
	private BigDecimal jurosTotal;
	
	@Column(name="MULTA_TOTAL")
	private BigDecimal multaTotal;
	
	@Column(name="HONORARIOS_TOTAL")
	private BigDecimal honorariosTotal;
	
	@Column(name="TIPO_PESSOA")
	@Enumerated(EnumType.STRING)
	private TipoPessoa	tipoPessoa;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_STATUS_DIVIDA")
	private  StatusDivida statusDivida;
	
	@Column(name="NUMERO_DIVIDA")
	private Long numeroDivida;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getQtdParcelas() {
		return qtdParcelas;
	}

	public void setQtdParcelas(Long qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}

	public Long getQtdParcelasPagas() {
		return qtdParcelasPagas;
	}

	public void setQtdParcelasPagas(Long qtdParcelasPagas) {
		this.qtdParcelasPagas = qtdParcelasPagas;
	}

	public Long getNumeroTermo() {
		return numeroTermo;
	}

	public void setNumeroTermo(Long numeroTermo) {
		this.numeroTermo = numeroTermo;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getJurosTotal() {
		return jurosTotal;
	}

	public void setJurosTotal(BigDecimal jurosTotal) {
		this.jurosTotal = jurosTotal;
	}

	public BigDecimal getMultaTotal() {
		return multaTotal;
	}

	public void setMultaTotal(BigDecimal multaTotal) {
		this.multaTotal = multaTotal;
	}

	public BigDecimal getHonorariosTotal() {
		return honorariosTotal;
	}

	public void setHonorariosTotal(BigDecimal honorariosTotal) {
		this.honorariosTotal = honorariosTotal;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public StatusDivida getStatusDivida() {
		return statusDivida;
	}

	public void setStatusDivida(StatusDivida statusDivida) {
		this.statusDivida = statusDivida;
	}

	public Long getNumeroDivida() {
		return numeroDivida;
	}

	public void setNumeroDivida(Long numeroDivida) {
		this.numeroDivida = numeroDivida;
	}

	
	

}
