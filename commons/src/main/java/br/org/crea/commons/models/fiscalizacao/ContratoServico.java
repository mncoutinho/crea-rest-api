package br.org.crea.commons.models.fiscalizacao;

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

import br.org.crea.commons.models.art.RamoArt;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.fiscalizacao.enuns.TipoAtividade;
import br.org.crea.commons.models.fiscalizacao.enuns.TipoContratacao;

@Entity
@Table(name="FIS_CONTRATOS")
@SequenceGenerator(name = "sqContratosFis", sequenceName = "FIS_CONTRATOS_SEQ", initialValue = 1, allocationSize = 1)
public class ContratoServico implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqContratosFis")
	private Long codigo;
	
	@OneToOne
	@JoinColumn(name="FK_PESSOA_CONTRATANTE")
	private Pessoa contratante;
	
	@OneToOne
	@JoinColumn(name="FK_PESSOA_CONTRATADO")
	private Pessoa contratado;
	
	@OneToOne
	@JoinColumn(name="FK_ATIVIDADE")
	private ContratoAtividade atividade;
	
	@Column(name="FK_ART")
	private String art;
	
	@Column(name="DATA_INICIO")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column(name="DATA_FINAL")
	@Temporal(TemporalType.DATE)
	private Date dataFinal;
	
	@Column(name="ATIVO")
	private boolean ativo;
	
	@Column(name="DATA_ATUALIZACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;
	
	@Column(name="CONTRATO_NOTAFISCAL")
	private String numeroDocumentoCondominio;
	
	@Column(name="EXISTE_CONTRATO")
	private boolean possuiContratoFormal;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO_CONTRATACAO")
	private TipoContratacao tipoContratacao;
	
	@Column(name="DATA_CONTRATO_SERVICO")
	private Date dataContratoServico;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO_ATIVIDADE")
	private TipoAtividade tipoAtividade;
	
	@OneToOne
	@JoinColumn(name="FK_RAMO")
	private RamoArt ramo;
	
	@Column(name="CONTRATO")
	private String contrato;
	
	@Column(name="PROCESSO")
	private String processo;
	
	@Column(name="PEDIDO_COMPRA")
	private String pedidoCompra;
	
	@Column(name="NOTA_FISCAL")
	private String notaFiscal;
	
	@Column(name="ORDEM_SERVICO")
	private String ordemServico;
	
	@Column(name="CARTA_CONVITE")
	private String cartaConvite;
	
	@Column(name="VALOR_EM_REAIS")
	private BigDecimal valorEmReais;
	
	@Column(name="ATIVIDADE_DESENVOLVIDA")
	private String atividadeDesenvolvida;
	
	@Column(name="OBJETO_CONTRATO")
	private String objetoContrato;

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Pessoa getContratante() {
		return contratante;
	}

	public void setContratante(Pessoa contratante) {
		this.contratante = contratante;
	}

	public Pessoa getContratado() {
		return contratado;
	}

	public void setContratado(Pessoa contratado) {
		this.contratado = contratado;
	}

	public ContratoAtividade getAtividade() {
		return atividade;
	}

	public void setAtividade(ContratoAtividade atividade) {
		this.atividade = atividade;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getNumeroDocumentoCondominio() {
		return numeroDocumentoCondominio;
	}

	public void setNumeroDocumentoCondominio(String numeroDocumentoCondominio) {
		this.numeroDocumentoCondominio = numeroDocumentoCondominio;
	}

	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public boolean isPossuiContratoFormal() {
		return possuiContratoFormal;
	}

	public void setPossuiContratoFormal(boolean possuiContratoFormal) {
		this.possuiContratoFormal = possuiContratoFormal;
	}

	public TipoContratacao getTipoContratacao() {
		return tipoContratacao;
	}

	public void setTipoContratacao(TipoContratacao tipoContratacao) {
		this.tipoContratacao = tipoContratacao;
	}

	public Date getDataContratoServico() {
		return dataContratoServico;
	}

	public void setDataContratoServico(Date dataContratoServico) {
		this.dataContratoServico = dataContratoServico;
	}

	public TipoAtividade getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(TipoAtividade tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	public RamoArt getRamo() {
		return ramo;
	}

	public void setRamo(RamoArt ramo) {
		this.ramo = ramo;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getPedidoCompra() {
		return pedidoCompra;
	}

	public void setPedidoCompra(String pedidoCompra) {
		this.pedidoCompra = pedidoCompra;
	}

	public String getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(String ordemServico) {
		this.ordemServico = ordemServico;
	}

	public String getCartaConvite() {
		return cartaConvite;
	}

	public void setCartaConvite(String cartaConvite) {
		this.cartaConvite = cartaConvite;
	}

	public BigDecimal getValorEmReais() {
		return valorEmReais;
	}

	public void setValorEmReais(BigDecimal valorEmReais) {
		this.valorEmReais = valorEmReais;
	}

	public String getAtividadeDesenvolvida() {
		return atividadeDesenvolvida;
	}

	public void setAtividadeDesenvolvida(String atividadeDesenvolvida) {
		this.atividadeDesenvolvida = atividadeDesenvolvida;
	}

	public String getObjetoContrato() {
		return objetoContrato;
	}

	public void setObjetoContrato(String objetoContrato) {
		this.objetoContrato = objetoContrato;
	}
	
}
