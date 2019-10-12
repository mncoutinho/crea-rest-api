package br.org.crea.commons.models.art;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ART_TAXA_ESPECIAL_M2")
public class TaxaEspecialMetroQuadrado implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="INICIO_VIGENCIA")
	@Temporal(TemporalType.DATE)
	private Date inicioVigencia;
	
	@Column(name="FIM_VIGENCIA")
	@Temporal(TemporalType.DATE)
	private Date fimVigencia;
	
	@Column(name="FAIXA_INICIAL")
	private BigDecimal faixaInicial;
	
	@Column(name="FAIXA_FINAL")
	private BigDecimal faixaFinal;
	
	@Column(name="EXEC")
	private BigDecimal exec;
	
	@Column(name="ARQ")
	private BigDecimal arq;
	
	@Column(name="EST")
	private BigDecimal est;
	
	@Column(name="ELE")
	private BigDecimal ele;
	
	@Column(name="HID")
	private BigDecimal hid;
	
	@Column(name="OU")
	private BigDecimal ou;
	
	@Column(name="MAXIMO")
	private BigDecimal maximo;
	
	@Column(name="FK_TIPO_TAXA")
	private Long tipoTaxa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public Date getFimVigencia() {
		return fimVigencia;
	}

	public void setFimVigencia(Date fimVigencia) {
		this.fimVigencia = fimVigencia;
	}

	public BigDecimal getFaixaInicial() {
		return faixaInicial;
	}

	public void setFaixaInicial(BigDecimal faixaInicial) {
		this.faixaInicial = faixaInicial;
	}

	public BigDecimal getFaixaFinal() {
		return faixaFinal;
	}

	public void setFaixaFinal(BigDecimal faixaFinal) {
		this.faixaFinal = faixaFinal;
	}

	public BigDecimal getExec() {
		return exec;
	}

	public void setExec(BigDecimal exec) {
		this.exec = exec;
	}

	public BigDecimal getArq() {
		return arq;
	}

	public void setArq(BigDecimal arq) {
		this.arq = arq;
	}

	public BigDecimal getEst() {
		return est;
	}

	public void setEst(BigDecimal est) {
		this.est = est;
	}

	public BigDecimal getEle() {
		return ele;
	}

	public void setEle(BigDecimal ele) {
		this.ele = ele;
	}

	public BigDecimal getHid() {
		return hid;
	}

	public void setHid(BigDecimal hid) {
		this.hid = hid;
	}

	public BigDecimal getOu() {
		return ou;
	}

	public void setOu(BigDecimal ou) {
		this.ou = ou;
	}

	public BigDecimal getMaximo() {
		return maximo;
	}

	public void setMaximo(BigDecimal maximo) {
		this.maximo = maximo;
	}

	public Long getTipoTaxa() {
		return tipoTaxa;
	}

	public void setTipoTaxa(Long tipoTaxa) {
		this.tipoTaxa = tipoTaxa;
	}

}
