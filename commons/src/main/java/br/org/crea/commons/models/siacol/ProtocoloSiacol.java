package br.org.crea.commons.models.siacol;

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

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Evento;
import br.org.crea.commons.models.commons.SituacaoProtocolo;
import br.org.crea.commons.models.protocolo.enuns.TipoProtocoloEnum;
import br.org.crea.commons.models.siacol.enuns.ClassificacaoProtocoloPautaEnum;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;


@Entity
@Table(name="SIACOL_PROTOCOLOS")
@SequenceGenerator(name="sqSiacolProtocolo",sequenceName="SQ_SIACOL_PROTOCOLOS",allocationSize = 1)
public class ProtocoloSiacol {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolProtocolo")
	private Long id;
	
	@Column(name="NO_PROTOCOLO")
	private Long numeroProtocolo;
	
	@Column(name="NO_PROCESSO")
	private Long numeroProcesso;
	
	@OneToOne
	@JoinColumn(name="FK_ASSUNTO_SIACOL")
	private AssuntoSiacol assuntoSiacol;	
	
	@Column(name="FK_ASSUNTO")
	private Long idAssuntoCorportativo;
	
	@Column(name="FK_CONSELHEIRO_RELATOR")
	private Long conselheiroRelator;
	
	@Column(name="FK_CONSELHEIRO_DEVOLUCAO")
	private Long conselheiroDevolucao;
	
	@Column(name="DS_CONSELHEIRO_RELATOR")
	private String nomeConselheiroRelator;
	
	@Column(name="FK_ANALISTA")
	private Long ultimoAnalista;
	
	@Column(name="DS_ASSUNTO")
	private String descricaoAssuntoCorporativo;
	
	@Column(name="DS_TIPO_ASSUNTO")
	private String descricaoTipoAssuntoCorporativo;
	
	@OneToOne
	@JoinColumn(name="FK_DEPARTAMENTO")
	private Departamento departamento;
	
	@OneToOne
	@JoinColumn(name="FK_SITUACAO_PROTOCOLO")
    private SituacaoProtocolo situacao;	
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@Column(name="DT_CADASTRO")
	private Date dataCadastro;
	
	@Column(name="FK_INTERESSADO")
	private Long idInteressado;
	
	@Column(name="DS_INTERESSADO")
	private String nomeInteressado;
	
	@Column(name="FK_RESPONSAVEL")
	private Long idResponsavel;
	
	@Column(name="DS_RESPONSAVEL")
	private String nomeResponsavel;
	
	@Column(name="DS_JUSTIFICATIVA")
	private String justificativa;
	
	@Column(name="DS_MOTIVO_DEVOLUCAO")
	private String motivoDevolucao;
	
	@Column(name="ATIVO")
	private Boolean ativo;
	
	@Column(name="RECEBIDO")
	private Boolean recebido;
	
	@Column(name="DT_RECEBIMENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRecebimento;
	
	@Column(name="DT_SIACOL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataSiacol;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private StatusProtocoloSiacol status;	
	
	@Enumerated(EnumType.STRING)
	@Column(name="CLASSIFICACAO")
	private ClassificacaoProtocoloPautaEnum classificacao;	
	
	@Enumerated(EnumType.STRING)
	@Column(name="ULTIMO_STATUS")
	private StatusProtocoloSiacol ultimoStatus;
	
	@Column(name="PROVISORIO")
	private Boolean provisorio;
	
	@Column(name="URGENCIA_VOTADO")
	private Boolean urgenciaVotado;
	
	@Column(name="AD_REFERENDUM")
	private Boolean adReferendum;
	
	@Column(name="HOMOLOGACAO_PF")
	private Boolean homologacaoPF;
	
	@Enumerated(EnumType.STRING)
	@Column(name="CLASSIFICACAO_FINAL")
	private ClassificacaoProtocoloPautaEnum classificacaoFinal;
	
	@OneToOne
	@JoinColumn(name="FK_PROTOCOLO_1_INSTANCIA")
    private ProtocoloSiacol protocoloPrimeiraInstancia;	
	
	@Column(name="NUMERO_DECISAO")
	private String numeroDecisao;
	
	@OneToOne
	@JoinColumn(name="FK_EVENTO")
    private Evento evento;	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public AssuntoSiacol getAssuntoSiacol() {
		return assuntoSiacol;
	}

	public void setAssuntoSiacol(AssuntoSiacol assuntoSiacol) {
		this.assuntoSiacol = assuntoSiacol;
	}

	public Long getIdAssuntoCorportativo() {
		return idAssuntoCorportativo;
	}

	public void setIdAssuntoCorportativo(Long idAssuntoCorportativo) {
		this.idAssuntoCorportativo = idAssuntoCorportativo;
	}

	public String getDescricaoAssuntoCorporativo() {
		return descricaoAssuntoCorporativo;
	}

	public void setDescricaoAssuntoCorporativo(String descricaoAssuntoCorporativo) {
		this.descricaoAssuntoCorporativo = descricaoAssuntoCorporativo;
	}

	public String getDescricaoTipoAssuntoCorporativo() {
		return descricaoTipoAssuntoCorporativo;
	}

	public void setDescricaoTipoAssuntoCorporativo(
			String descricaoTipoAssuntoCorporativo) {
		this.descricaoTipoAssuntoCorporativo = descricaoTipoAssuntoCorporativo;
	}

	public SituacaoProtocolo getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProtocolo situacao) {
		this.situacao = situacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getIdInteressado() {
		return idInteressado;
	}

	public void setIdInteressado(Long idInteressado) {
		this.idInteressado = idInteressado;
	}

	public String getNomeInteressado() {
		return nomeInteressado;
	}

	public void setNomeInteressado(String nomeInteressado) {
		this.nomeInteressado = nomeInteressado;
	}

	public Long getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(Long idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public boolean temAssunto() {
		return this.assuntoSiacol != null ? true : false;
	}

	public boolean temDepartamento() {
		return this.departamento != null ? true : false;
	}

	public boolean temSituacao() {
		return this.situacao != null ? true : false;
	}
	
	public boolean temStatus() {
		return this.status != null ? true : false;
	}
	
	public boolean temUltimoStatus() {
		return this.ultimoStatus != null ? true : false;
	}
	
	public boolean temConselheiroRelator() {
		return this.conselheiroRelator != null ? true : false;
	}

	public boolean temConselheiroDevolucao() {
		return this.conselheiroDevolucao != null ? true : false;
	}

	public boolean temRecebido() {
		return this.conselheiroDevolucao != null ? true : false;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	
	public Boolean getRecebido() {
		return recebido;
	}

	public void setRecebido(Boolean recebido) {
		this.recebido = recebido;
	}
	
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getMotivoDevolucao() {
		return motivoDevolucao;
	}

	public void setMotivoDevolucao(String motivoDevolucao) {
		this.motivoDevolucao = motivoDevolucao;
	}
	
	public Long getConselheiroRelator() {
		return conselheiroRelator;
	}

	public void setConselheiroRelator(Long conselheiroRelator) {
		this.conselheiroRelator = conselheiroRelator;
	}
	
	public String getNomeConselheiroRelator() {
		return nomeConselheiroRelator;
	}

	public Long getConselheiroDevolucao() {
		return conselheiroDevolucao;
	}

	public void setConselheiroDevolucao(Long conselheiroDevolucao) {
		this.conselheiroDevolucao = conselheiroDevolucao;
	}

	public void setNomeConselheiroRelator(String nomeConselheiroRelator) {
		this.nomeConselheiroRelator = nomeConselheiroRelator;
	}	

	public Long getUltimoAnalista() {
		return ultimoAnalista;
	}

	public void setUltimoAnalista(Long ultimoAnalista) {
		this.ultimoAnalista = ultimoAnalista;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Date getDataSiacol() {
		return dataSiacol;
	}

	public void setDataSiacol(Date dataSiacol) {
		this.dataSiacol = dataSiacol;
	}
	
	public StatusProtocoloSiacol getStatus() {
		return status;
	}

	public void setStatus(StatusProtocoloSiacol status) {
		this.status = status;
	}

	public ClassificacaoProtocoloPautaEnum getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(ClassificacaoProtocoloPautaEnum classificacao) {
		this.classificacao = classificacao;
	}

	public StatusProtocoloSiacol getUltimoStatus() {
		return ultimoStatus;
	}

	public void setUltimoStatus(StatusProtocoloSiacol ultimoStatus) {
		this.ultimoStatus = ultimoStatus;
	}

	public Boolean getProvisorio() {
		return provisorio;
	}

	public void setProvisorio(Boolean provisorio) {
		this.provisorio = provisorio;
	}

	public Boolean getUrgenciaVotado() {
		return urgenciaVotado;
	}

	public void setUrgenciaVotado(Boolean urgenciaVotado) {
		this.urgenciaVotado = urgenciaVotado;
	}

	public Boolean getAdReferendum() {
		return adReferendum;
	}

	public void setAdReferendum(Boolean adReferendum) {
		this.adReferendum = adReferendum;
	}

	public ClassificacaoProtocoloPautaEnum getClassificacaoFinal() {
		return classificacaoFinal;
	}

	public void setClassificacaoFinal(
			ClassificacaoProtocoloPautaEnum classificacaoFinal) {
		this.classificacaoFinal = classificacaoFinal;
	}

	public TipoProtocoloEnum getTipoProtocolo() {
		
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
	}

	public boolean temProvisorio() {
		return this.provisorio != null;
	}

	public boolean temAdReferendum() {
		return this.adReferendum != null;
	}

	public boolean temUrgenciaVotado() {
		return this.urgenciaVotado != null;
	}

	public String getNumeroDecisao() {
		return numeroDecisao;
	}

	public void setNumeroDecisao(String numeroDecisao) {
		this.numeroDecisao = numeroDecisao;
	}

	public Boolean getHomologacaoPF() {
		return homologacaoPF;
	}

	public void setHomologacaoPF(Boolean homologacaoPF) {
		this.homologacaoPF = homologacaoPF;
	}

	public boolean temHomologacaoPF() {
		return this.homologacaoPF != null;
	}

	public boolean temProtocoloPrimeiraInstancia() {
		return this.protocoloPrimeiraInstancia != null;
	}
	
	
	public ProtocoloSiacol getProtocoloPrimeiraInstancia() {
		return protocoloPrimeiraInstancia;
	}

	public void setProtocoloPrimeiraInstancia(ProtocoloSiacol protocoloPrimeiraInstancia) {
		this.protocoloPrimeiraInstancia = protocoloPrimeiraInstancia;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public boolean temEvento() {
		return this.evento != null;
	}

	public boolean temJustificativa() {
		return this.justificativa != null;
	}

	public boolean temClassificacao() {
		return this.classificacao != null;
	}

}


