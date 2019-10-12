package br.org.crea.commons.models.cadastro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="CAD_CERTIDAO")
@SequenceGenerator(name="CERTIDAO_SEQUENCE",sequenceName="CAD_CERTIDAO_SEQ",allocationSize = 1)
public class Certidao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CERTIDAO_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="NUMERO")
	private String numero;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO_PESSOA")
	private TipoPessoa tipoPessoa;
	
	@Column(name="NOSSO_NUMERO")
	private String nossoNumero;
	
	@Column(name="DATA_EMISSAO")
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;
	
	@Column(name="DATA_REEMISSAO")
	@Temporal(TemporalType.DATE)
	private Date dataReemissao;
	
	@Column(name="DATA_ENTREGA")
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;
	
	@Column(name="DATA_CANC")
	@Temporal(TemporalType.DATE)
	private Date dataCancelamento;
		
	@Column(name="CANCELADA")
	private Boolean cancelada;
	
	@Column(name="MOTIVO_CANCELAMENTO")
	private String motivoDoCancelamento;
	
	@Column(name="ANO")
	private Long anoCertidao;
	
	@Column(name="DATA_VALIDADE")
	@Temporal(TemporalType.DATE)
	private Date dataValidade;
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@Column(name="RESSALVA")
	private String ressalva;
	
	@Column(name="MOTIVO_REMISSAO")
	private String motivoRemissao;
	
	@Column(name="QUANTIDADE_ATESTADO")
	private Long quantidadeAtestado;

	@OneToOne
	@JoinColumn(name="FK_CODIGO_MOTIVO_CANC_CERTIDAO")
	private MotivoCancelamentoCertidao motivoCancelamento;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_TIPO_CERTIDAO")
	private TipoCertidao tipo;
	
	@Column(name="NUM_CONTROLE")
	private String numeroAutenticacao;
	
	@Column(name="DATA_AUTENTICACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAutenticacao;

	@Column(name="LIBERADA")
	private Boolean liberada;
	
	@Column(name="ID_DOCFLOW")
	private Long idDocflow;
	
	@Column(name="COPIA_CERTIDAO")
	@Lob
	private byte[] copia;
	
	@Transient
	private Boolean anoVigente = true;
	
	@Transient
	private String finalidade;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_PESSOA")
	private Pessoa pessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataReemissao() {
		return dataReemissao;
	}

	public void setDataReemissao(Date dataReemissao) {
		this.dataReemissao = dataReemissao;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Boolean getCancelada() {
		return cancelada;
	}

	public void setCancelada(Boolean cancelada) {
		this.cancelada = cancelada;
	}

	public String getMotivoDoCancelamento() {
		return motivoDoCancelamento;
	}

	public void setMotivoDoCancelamento(String motivoDoCancelamento) {
		this.motivoDoCancelamento = motivoDoCancelamento;
	}

	public Long getAnoCertidao() {
		return anoCertidao;
	}

	public void setAnoCertidao(Long anoCertidao) {
		this.anoCertidao = anoCertidao;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getRessalva() {
		return ressalva;
	}

	public void setRessalva(String ressalva) {
		this.ressalva = ressalva;
	}

	public String getMotivoRemissao() {
		return motivoRemissao;
	}

	public void setMotivoRemissao(String motivoRemissao) {
		this.motivoRemissao = motivoRemissao;
	}

	public Long getQuantidadeAtestado() {
		return quantidadeAtestado;
	}

	public void setQuantidadeAtestado(Long quantidadeAtestado) {
		this.quantidadeAtestado = quantidadeAtestado;
	}

	public MotivoCancelamentoCertidao getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(MotivoCancelamentoCertidao motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public TipoCertidao getTipo() {
		return tipo;
	}

	public void setTipo(TipoCertidao tipo) {
		this.tipo = tipo;
	}

	public String getNumeroAutenticacao() {
		return numeroAutenticacao;
	}

	public void setNumeroAutenticacao(String numeroAutenticacao) {
		this.numeroAutenticacao = numeroAutenticacao;
	}

	public Date getDataAutenticacao() {
		return dataAutenticacao;
	}

	public void setDataAutenticacao(Date dataAutenticacao) {
		this.dataAutenticacao = dataAutenticacao;
	}

	public Boolean getLiberada() {
		return liberada;
	}

	public void setLiberada(Boolean liberada) {
		this.liberada = liberada;
	}

	public Long getIdDocflow() {
		return idDocflow;
	}

	public void setIdDocflow(Long idDocflow) {
		this.idDocflow = idDocflow;
	}

	public byte[] getCopia() {
		return copia;
	}

	public void setCopia(byte[] copia) {
		this.copia = copia;
	}

	public Boolean getAnoVigente() {
		return anoVigente;
	}

	public void setAnoVigente(Boolean anoVigente) {
		this.anoVigente = anoVigente;
	}

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	
	
	
	
	
}

