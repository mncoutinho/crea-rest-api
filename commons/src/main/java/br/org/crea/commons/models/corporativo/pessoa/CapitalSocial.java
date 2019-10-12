package br.org.crea.commons.models.corporativo.pessoa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CAD_CAPITAIS_SOCIAIS")
public class CapitalSocial implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO")
	private Long codigo;

	@ManyToOne
	@JoinColumn(name = "FK_CODIGO_EMPRESAS")
	private Empresa empresa;

	@Column(name = "DATAINCLUSAO")
	@Temporal(TemporalType.DATE)
	private Date dataInclusao;
	
	@Column(name = "DATA_ALTERACAO")
	@Temporal(TemporalType.DATE)
	private Date dataAlteracao;

	@Column(name = "DATAJUNTA")
	@Temporal(TemporalType.DATE)
	private Date dataJuntaComercial;

	@Column(name = "DATARECEITA")
	@Temporal(TemporalType.DATE)
	private Date dataReceitaFederal;

	@Column(name = "DATAINTEGRALIZAR")
	@Temporal(TemporalType.DATE)
	private Date dataIntegralizacao;

	@Column(name = "FAIXACAPITAL")
	private Long faixaCapital;

	@Column(name = "VALORCAPITAL")
	private BigDecimal valorCapital;

	@Column(name = "VCAPITALMOEDACORRENTE")
	private BigDecimal valorCapitalMoedaCorrente;

	@Column(name = "CAPITALINTEGRALIZADO")
	private BigDecimal valorCapitalIntegralizado;

	@Column(name = "CAPITALINTEGRALIZAR")
	private BigDecimal valorCapitalAIntegralizar;
	
	@Column(name = "ATIVO")
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "FK_TIPOS_CAPITAIS_SOCIAIS")
	private TipoCapitalSocial tipoCapitalSocial;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataJuntaComercial() {
		return dataJuntaComercial;
	}

	public void setDataJuntaComercial(Date dataJuntaComercial) {
		this.dataJuntaComercial = dataJuntaComercial;
	}

	public Date getDataReceitaFederal() {
		return dataReceitaFederal;
	}

	public void setDataReceitaFederal(Date dataReceitaFederal) {
		this.dataReceitaFederal = dataReceitaFederal;
	}

	public Date getDataIntegralizacao() {
		return dataIntegralizacao;
	}

	public void setDataIntegralizacao(Date dataIntegralizacao) {
		this.dataIntegralizacao = dataIntegralizacao;
	}

	public Long getFaixaCapital() {
		return faixaCapital;
	}

	public void setFaixaCapital(Long faixaCapital) {
		this.faixaCapital = faixaCapital;
	}

	public BigDecimal getValorCapital() {
		return valorCapital;
	}

	public void setValorCapital(BigDecimal valorCapital) {
		this.valorCapital = valorCapital;
	}

	public BigDecimal getValorCapitalMoedaCorrente() {
		return valorCapitalMoedaCorrente;
	}

	public void setValorCapitalMoedaCorrente(BigDecimal valorCapitalMoedaCorrente) {
		this.valorCapitalMoedaCorrente = valorCapitalMoedaCorrente;
	}

	public BigDecimal getValorCapitalIntegralizado() {
		return valorCapitalIntegralizado;
	}

	public void setValorCapitalIntegralizado(BigDecimal valorCapitalIntegralizado) {
		this.valorCapitalIntegralizado = valorCapitalIntegralizado;
	}

	public BigDecimal getValorCapitalAIntegralizar() {
		return valorCapitalAIntegralizar;
	}

	public void setValorCapitalAIntegralizar(BigDecimal valorCapitalAIntegralizar) {
		this.valorCapitalAIntegralizar = valorCapitalAIntegralizar;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public TipoCapitalSocial getTipoCapitalSocial() {
		return tipoCapitalSocial;
	}

	public void setTipoCapitalSocial(TipoCapitalSocial tipoCapitalSocial) {
		this.tipoCapitalSocial = tipoCapitalSocial;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

}
