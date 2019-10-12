package br.org.crea.commons.models.commons;

import java.util.Date;
import java.util.Optional;

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
import javax.persistence.Transient;

import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;

@Entity
@Table(name = "PRT_PROTOCOLOS")
public class Protocolo {

	@Id
	@Column(name = "ID")
	private Long idProtocolo;

	@Column(name = "NUMERO")
	private Long numeroProtocolo;

	@Column(name = "PROCESSO")
	private Long numeroProcesso;

	@OneToOne
	@JoinColumn(name = "FK_ID_PESSOAS")
	private Pessoa pessoa;

	@OneToOne
	@JoinColumn(name = "FK_ID_ASSUNTOS")
	private Assunto assunto;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATAEMISSAO")
	private Date dataEmissao;

	@Column(name = "OBSERVACAOPROTOCOLO")
	private String observacao;

	@OneToOne
	@JoinColumn(name = "PRIMEIROMOVIMENTO")
	private Movimento primeiroMovimento;

	@OneToOne
	@JoinColumn(name = "ULTIMOMOVIMENTO")
	private Movimento ultimoMovimento;

	@Column(name = "FK_ID_PROTOCOLOS_APENSO")
	private Long idProtocoloPaiApenso;

	@Column(name = "FK_ID_PROTOCOLOS_ANEXO")
	private Long idProtocoloPaiAnexo;

	@Column(name = "FK_ID_PROTOCOLOS_SUBSTITUTO")
	private Long idProtocoloSubstituto;

	@Column(name = "FK_STATUS_TRANSACAO")
	private Long idStatusTransacao;

	@Column(name = "FK_UNIDADE_ATENDIMENTO")
	private Long idUnidadeAtendimento;

	@Column(name = "EXCLUIDO")
	private boolean excluido;

	@Column(name = "FINALIZADO")
	private boolean finalizado;

	@Column(name = "POSSUI_DIGITALIZACAO")
	private boolean digital;

	@Column(name = "FK_ID_FUNCIONARIOS")
	private Long idFuncionario;

	@Column(name = "REGISTROINTERESSADO")
	private String interessado;

	/**
	 * Data que é preenchida qdo protocolo nasceu físico e foi digitalizado
	 * posteriormente
	 **/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_DIGITALIZACAO")
	private Date dataDigitalizacao;

	@Column(name = "TIPOPROTOCOLO")
	@Enumerated(EnumType.ORDINAL)
	private TipoProtocoloEnum tipoProtocolo;

	@Column(name = "TIPOPESSOA")
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;

	@Column(name = "AI")
	private Long ai;

	@Column(name = "NOTIFICACAO")
	private Long notificacao;

	@Transient
	private Long idSituacaoTramite;

	@Transient
	private Long idObservacaoTramite;

	public Long getIdProtocolo() {
		return idProtocolo;
	}

	public void setIdProtocolo(Long idProtocolo) {
		this.idProtocolo = idProtocolo;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Long getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(Long numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Movimento getPrimeiroMovimento() {
		return primeiroMovimento;
	}

	public void setPrimeiroMovimento(Movimento primeiroMovimento) {
		this.primeiroMovimento = primeiroMovimento;
	}

	public Movimento getUltimoMovimento() {
		return ultimoMovimento;
	}

	public void setUltimoMovimento(Movimento ultimoMovimento) {
		this.ultimoMovimento = ultimoMovimento;
	}

	public Long getIdProtocoloPaiApenso() {
		return idProtocoloPaiApenso;
	}

	public void setIdProtocoloPaiApenso(Long idProtocoloPaiApenso) {
		this.idProtocoloPaiApenso = idProtocoloPaiApenso;
	}

	public Long getIdProtocoloPaiAnexo() {
		return idProtocoloPaiAnexo;
	}

	public void setIdProtocoloPaiAnexo(Long idProtocoloPaiAnexo) {
		this.idProtocoloPaiAnexo = idProtocoloPaiAnexo;
	}

	public Long getIdProtocoloSubstituto() {
		return idProtocoloSubstituto;
	}

	public void setIdProtocoloSubstituto(Long idProtocoloSubstituto) {
		this.idProtocoloSubstituto = idProtocoloSubstituto;
	}

	public Long getIdStatusTransacao() {
		return idStatusTransacao;
	}

	public void setIdStatusTransacao(Long idStatusTransacao) {
		this.idStatusTransacao = idStatusTransacao;
	}

	public boolean isExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public boolean isDigital() {
		return digital;
	}

	public void setDigital(boolean digital) {
		this.digital = digital;
	}

	public Long getIdSituacaoTramite() {
		return idSituacaoTramite;
	}

	public void setIdSituacaoTramite(Long idSituacaoTramite) {
		this.idSituacaoTramite = idSituacaoTramite;
	}

	public Long getIdObservacaoTramite() {
		return idObservacaoTramite;
	}

	public void setIdObservacaoTramite(Long idObservacaoTramite) {
		this.idObservacaoTramite = idObservacaoTramite;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Date getDataDigitalizacao() {
		return dataDigitalizacao;
	}

	public void setDataDigitalizacao(Date dataDigitalizacao) {
		this.dataDigitalizacao = dataDigitalizacao;
	}

	public boolean protocoloEstaExcluido() {
		return this.excluido ? true : false;
	}

	public boolean protocoloEstaSubstituido() {
		return this.idProtocoloSubstituto != null;
	}

	public boolean protocoloEstaAnexado() {
		return this.idProtocoloPaiAnexo != null;
	}

	public boolean protocoloEstaApensado() {
		return this.idProtocoloPaiApenso != null;
	}

	public boolean protocoloEstaComStatusDivergente() {
		return this.idStatusTransacao != null && !this.idStatusTransacao.equals(new Long(0)) ? true : false;
	}

	public boolean assuntoEhEntregaCarteira() {
		return assunto.getId().equals(new Long(1006)) ? true : false;
	}

	public boolean assuntoEhBaixaQuadroTecnico() {
		return assunto.getId().equals(new Long(2012)) ? true : false;
	}

	public TipoProtocoloEnum getTipoProtocolo() {

		if (String.valueOf(numeroProtocolo).length() >= 4) {

			switch (String.valueOf(numeroProtocolo).charAt(4)) {
			case '0':
				return TipoProtocoloEnum.LEIGO;
			case '1':
				return TipoProtocoloEnum.PROFISSIONAL;
			case '2':
				return TipoProtocoloEnum.EMPRESA;
			case '3':
				return TipoProtocoloEnum.AUTOINFRACAO;
			case '4':
				return TipoProtocoloEnum.ADMINISTRATIVO_FINANCEIRO;
			case '5':
				return TipoProtocoloEnum.OUTROS_TIPOS;
			case '6':
				return TipoProtocoloEnum.NOTIFICACAO_OFICIO;
			case '7':
				return TipoProtocoloEnum.PROTOCOLO;
			case '8':
				return TipoProtocoloEnum.ENTIDADE_CLASSE_ENSINO;
			case '9':
				return TipoProtocoloEnum.AUTOINFRACAO_EXTERNO;
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	public void setTipoProtocolo(TipoProtocoloEnum tipoProtocolo) {
		this.tipoProtocolo = tipoProtocolo;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Optional<Movimento> optionalPrimeiroMovimento() {
		return Optional.ofNullable(this.primeiroMovimento);
	}

	public Optional<Movimento> optionalUltimoMovimento() {
		return Optional.ofNullable(this.ultimoMovimento);
	}

	public Long getAi() {
		return ai;
	}

	public void setAi(Long ai) {
		this.ai = ai;
	}

	public Long getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Long notificacao) {
		this.notificacao = notificacao;
	}

	public String getInteressado() {
		return interessado;
	}

	public void setInteressado(String interessado) {
		this.interessado = interessado;
	}

	public Long getIdUnidadeAtendimento() {
		return idUnidadeAtendimento;
	}

	public void setIdUnidadeAtendimento(Long idUnidadeAtendimento) {
		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	public boolean temAssunto() {
		return this.assunto != null;
	}

	public boolean temNumeroProtocolo() {
		return this.numeroProtocolo != null;
	}

}
