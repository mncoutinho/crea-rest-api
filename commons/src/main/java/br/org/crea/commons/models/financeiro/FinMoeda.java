package br.org.crea.commons.models.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="FIN_MOEDA")
public class FinMoeda implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5638231631348805196L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CODIGO")
	private Long codigo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="INICIO_VIGENCIA")
	private Date vigenciaInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FINAL_VIGENCIA")
	private Date vigenciaFim;
	
	@Column(name="DENOMINACAO")
	private String denominacao;

	@Column(name="SIMBOLO")
	private String simbolo;
	
	@Column(name="OPERADOR")
	private Character operador;
	
	@Column(name="OPERADOR_CONVERSAO")
	private Character operadorConversao;
	
	@Column(name="FATOR_CONVERSAO")
	private BigDecimal fatorConversao;
	
	@Column(name="PARIDADE")
	private BigDecimal paridade;
	
	@Column(name="OBSERVACOES")
	private String observacao;
	
	@Column(name="FUNDAMENTO_LEGAL")
	private String fundamentoLegal;
	
	@Column(name="EXTENSO_PLURAL")
	private String extensoPlural;
	
	@Column(name="EXTENSO_SINGULAR")
	private String extensoSingular;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getVigenciaInicio() {
		return vigenciaInicio;
	}

	public void setVigenciaInicio(Date vigenciaInicio) {
		this.vigenciaInicio = vigenciaInicio;
	}

	public Date getVigenciaFim() {
		return vigenciaFim;
	}

	public void setVigenciaFim(Date vigenciaFim) {
		this.vigenciaFim = vigenciaFim;
	}

	public String getDenominacao() {
		return denominacao;
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public Character getOperador() {
		return operador;
	}

	public void setOperador(Character operador) {
		this.operador = operador;
	}

	public Character getOperadorConversao() {
		return operadorConversao;
	}

	public void setOperadorConversao(Character operadorConversao) {
		this.operadorConversao = operadorConversao;
	}

	public BigDecimal getFatorConversao() {
		return fatorConversao;
	}

	public void setFatorConversao(BigDecimal fatorConversao) {
		this.fatorConversao = fatorConversao;
	}

	public BigDecimal getParidade() {
		return paridade;
	}

	public void setParidade(BigDecimal paridade) {
		this.paridade = paridade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getFundamentoLegal() {
		return fundamentoLegal;
	}

	public void setFundamentoLegal(String fundamentoLegal) {
		this.fundamentoLegal = fundamentoLegal;
	}

	public String getExtensoPlural() {
		return extensoPlural;
	}

	public void setExtensoPlural(String extensoPlural) {
		this.extensoPlural = extensoPlural;
	}

	public String getExtensoSingular() {
		return extensoSingular;
	}

	public void setExtensoSingular(String extensoSingular) {
		this.extensoSingular = extensoSingular;
	}

	
}
