package br.org.crea.commons.models.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.commons.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name = "FIN_DIVIDA")
@SequenceGenerator(name = "DIVIDA_SEQUENCE", sequenceName = "FIN_DIVIDA_SEQ", initialValue = 1, allocationSize = 1)
public class FinDivida implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIVIDA_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_NATUREZA")
	private NaturezaDivida natureza;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_STATUS_DIVIDA")
	private StatusDivida status;
	
	@Column(name="DUODECIMOS")
	private String duodecimos;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO_PESSOA")
	private TipoPessoa tipoPessoa;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_PESSOA")
	private Pessoa pessoa;
	
	@Column(name="PARCELA")
	private Integer parcela;
	
	@Column(name="IDENTIFICADOR_DIVIDA")
	private String identificadorDivida;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA")
	private Date data;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_VENCIMENTO")
	private Date dataVencimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_QUITACAO")
	private Date dataQuitacao;
	
	@Column(name="VALOR_ORIGINAL")
	private BigDecimal valorOriginal;
	
	@Column(name="HONORARIOS")
	private BigDecimal honorarios;
	
	@Column(name="VALOR_ATUAL")
	private BigDecimal valorAtual;
	
	@Column(name="VALOR_PAGO")
	private BigDecimal valorPago;
	
	@Column(name="JUROS")
	private BigDecimal juros;
	
	@Column(name="MULTA")
	private BigDecimal multa;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_VALOR_ATUALIZADO")
	private Date dataValorAtualizado;
	
	@Column(name="NOSSO_NUMERO")
	private String nossoNumero;
	
	@Column(name="SERVICO_EXECUTADO")
	private Boolean servicoExecutado;
	
	@Column(name="DIATI")
	private boolean diati;
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_LOCAL_PGTO")
	private FinLocalPagamento localPgto;
	
	@Column(name="SCPC")
	private Boolean scpc;
	
	@Column(name="SCPC_BAIXA")
	private Boolean scpcBaixa;
	
	@Column(name="SCPC_REPASSE")
	private Boolean scpcRepasse;
	
	@Column(name="REPASSE_OK")
	private Boolean repasseOk;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NaturezaDivida getNatureza() {
		return natureza;
	}

	public void setNatureza(NaturezaDivida natureza) {
		this.natureza = natureza;
	}

	public StatusDivida getStatus() {
		return status;
	}

	public void setStatus(StatusDivida status) {
		this.status = status;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Integer getParcela() {
		return parcela;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}

	public String getIdentificadorDivida() {
		return identificadorDivida;
	}

	public void setIdentificadorDivida(String identificadorDivida) {
		this.identificadorDivida = identificadorDivida;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataQuitacao() {
		return dataQuitacao;
	}

	public void setDataQuitacao(Date dataQuitacao) {
		this.dataQuitacao = dataQuitacao;
	}

	public BigDecimal getValorOriginal() {
		return valorOriginal;
	}

	public void setValorOriginal(BigDecimal valorOriginal) {
		this.valorOriginal = valorOriginal;
	}

	public BigDecimal getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(BigDecimal honorarios) {
		this.honorarios = honorarios;
	}

	public BigDecimal getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(BigDecimal valorAtual) {
		this.valorAtual = valorAtual;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Date getDataValorAtualizado() {
		return dataValorAtualizado;
	}

	public void setDataValorAtualizado(Date dataValorAtualizado) {
		this.dataValorAtualizado = dataValorAtualizado;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Boolean getServicoExecutado() {
		return servicoExecutado;
	}

	public void setServicoExecutado(Boolean servicoExecutado) {
		this.servicoExecutado = servicoExecutado;
	}

	public boolean isDiati() {
		return diati;
	}

	public void setDiati(boolean diati) {
		this.diati = diati;
	}	
	
	public boolean temDiati() {
		return this.diati ? true : false;
	}

	public FinLocalPagamento getLocalPgto() {
		return localPgto;
	}

	public void setLocalPgto(FinLocalPagamento localPgto) {
		this.localPgto = localPgto;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Boolean getScpc() {
		return scpc;
	}

	public void setScpc(Boolean scpc) {
		this.scpc = scpc;
	}

	public Boolean getScpcBaixa() {
		return scpcBaixa;
	}

	public void setScpcBaixa(Boolean scpcBaixa) {
		this.scpcBaixa = scpcBaixa;
	}

	public Boolean getScpcRepasse() {
		return scpcRepasse;
	}

	public void setScpcRepasse(Boolean scpcRepasse) {
		this.scpcRepasse = scpcRepasse;
	}

	public Boolean getRepasseOk() {
		return repasseOk;
	}

	public void setRepasseOk(Boolean repasseOk) {
		this.repasseOk = repasseOk;
	}

	public String getDuodecimos() {
		return duodecimos;
	}

	public void setDuodecimos(String duodecimos) {
		this.duodecimos = duodecimos;
	}

	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}
	
}
