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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.financeiro.enuns.StatusEmissaoBoletoEnum;

@Entity
@Table(name = "FIN_BOLETO")
@SequenceGenerator(name = "BOLETO_SEQUENCE", sequenceName = "FIN_BOLETO_SEQ", initialValue = 1, allocationSize = 1)
public class Boleto implements Serializable {
	
	private static final long serialVersionUID = 2373689793862443052L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOLETO_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="FK_CODIGO_PESSOA")
	private Long idPessoa;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_VENCIMENTO")
	private Date dataVencimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_PAGAMENTO")
	private Date dataPagamento;
	
	@Column(name="VALOR_ORIGINAL")
	private BigDecimal valorOriginal;
	
	@Column(name="VALOR_ATUAL")
	private BigDecimal valorAtual;
	
	@Column(name="DESCONTO_ABATIMENTO")
	private BigDecimal descontoAbatimento;
	
	@Column(name="FK_CODIGO_CONVENIO")
	private Long idConvenio;
	
	@Column(name="ATIVO")
	private Boolean ativo;
	
	@Column(name="INSTRUCAO")
	private String instrucao;
	
	@Column(name="NOSSO_NUMERO")
	private String nossoNumero;
	
	@Column(name="FK_CODIGO_STATUS_BOLETO")
	private Long idStatusBoleto;
	
	@Column(name="COTA")
	private Long cota;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS_EMISSAO", length=20)
	private StatusEmissaoBoletoEnum statusEmissao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValorOriginal() {
		return valorOriginal;
	}

	public void setValorOriginal(BigDecimal valorOriginal) {
		this.valorOriginal = valorOriginal;
	}

	public BigDecimal getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(BigDecimal valorAtual) {
		this.valorAtual = valorAtual;
	}

	public BigDecimal getDescontoAbatimento() {
		return descontoAbatimento;
	}

	public void setDescontoAbatimento(BigDecimal descontoAbatimento) {
		this.descontoAbatimento = descontoAbatimento;
	}

	public Long getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Long idConvenio) {
		this.idConvenio = idConvenio;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getInstrucao() {
		return instrucao;
	}

	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Long getCota() {
		return cota;
	}

	public void setCota(Long cota) {
		this.cota = cota;
	}

	public StatusEmissaoBoletoEnum getStatusEmissao() {
		return statusEmissao;
	}

	public void setStatusEmissao(StatusEmissaoBoletoEnum statusEmissao) {
		this.statusEmissao = statusEmissao;
	}

	public Long getIdStatusBoleto() {
		return idStatusBoleto;
	}

	public void setIdStatusBoleto(Long idStatusBoleto) {
		this.idStatusBoleto = idStatusBoleto;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public boolean temDataPagamento() {
		return this.dataPagamento != null;
	}
	
	
	
}
