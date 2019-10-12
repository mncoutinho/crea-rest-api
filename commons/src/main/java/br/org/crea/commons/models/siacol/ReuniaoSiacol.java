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
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.siacol.enuns.StatusReuniaoSiacol;


@Entity
@Table(name="SIACOL_REUNIAO")
@SequenceGenerator(name="sqSiacolReuniao",sequenceName="SQ_SIACOL_REUNIAO",allocationSize = 1)
public class ReuniaoSiacol {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolReuniao")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_DEPARTAMENTO")
	private Departamento departamento;
	
	@OneToOne
	@JoinColumn(name="FK_DOCUMENTO")
	private Documento pauta;
	
	@OneToOne
	@JoinColumn(name="FK_SUMULA")
	private Documento sumula;
	
	@OneToOne
	@JoinColumn(name="FK_EXTRA_PAUTA")
	private Documento extraPauta;
	
	@Column(name="DS_LOCAL")
	private String local;
	
	@Column(name="TIPO")
	private Long tipo;
	
	@Column(name="VIRTUAL")
	private Boolean virtual;
	
	@Column(name="DATA_REUNIAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataReuniao;
	
	@Column(name="HR_INICIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaInicio;
	
	@Column(name="HR_FIM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaFim;
	
	@Column(name="PRAZO")
	private Integer prazo;
	
	@Column(name="QUORUM")
	private Integer quorum;
	
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private StatusReuniaoSiacol status;
	
	@Column(name="STATUS_PAINEL")
	private String statusPainel;
	
	@Column(name="MOTIVO_CANCELAMENTO")
	private String motivoCancelamento;
	
	@Column(name="HOUVE_PAUSA")
	private Boolean houvePausa;
	
	@Column(name="PARTE")
	private Long parte;
	
	@OneToOne
	@JoinColumn(name="FK_ARQUIVO_NAO_ASSINADO")
	private Arquivo arquivoNaoAssinado;
	
	@OneToOne
	@JoinColumn(name="FK_ARQUIVO_ASSINADO")
	private Arquivo arquivoAssinado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Documento getPauta() {
		return pauta;
	}

	public void setPauta(Documento pauta) {
		this.pauta = pauta;
	}

	public Documento getExtraPauta() {
		return extraPauta;
	}

	public void setExtraPauta(Documento extraPauta) {
		this.extraPauta = extraPauta;
	}

	public Documento getSumula() {
		return sumula;
	}

	public void setSumula(Documento sumula) {
		this.sumula = sumula;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public Boolean getVirtual() {
		return virtual;
	}

	public void setVirtual(Boolean virtual) {
		this.virtual = virtual;
	}

	public Date getDataReuniao() {
		return dataReuniao;
	}

	public void setDataReuniao(Date dataReuniao) {
		this.dataReuniao = dataReuniao;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public Integer getPrazo() {
		return prazo;
	}

	public void setPrazo(Integer prazo) {
		this.prazo = prazo;
	}

	public StatusReuniaoSiacol getStatus() {
		return status;
	}

	public void setStatus(StatusReuniaoSiacol status) {
		this.status = status;
	}

	public boolean temPauta() {
		return this.pauta != null;
	}
	
	public boolean temExtraPauta() {
		return this.extraPauta != null;
	}
	
	public boolean temSumula() {
		return this.sumula != null;
	}
	
	public boolean temArquivoNaoAssinado() {
		return this.arquivoNaoAssinado != null;
	}
	
	public boolean temArquivoAssinado() {
		return this.arquivoAssinado != null;
	}
	
	public Integer getQuorum() {
		return quorum;
	}

	public void setQuorum(Integer quorum) {
		this.quorum = quorum;
	}

	public boolean estaAbertaParaVotacao() {
		return this.getStatus().getId() == 2;
	}

	public String getStatusPainel() {
		return statusPainel;
	}

	public void setStatusPainel(String statusPainel) {
		this.statusPainel = statusPainel;
	}

	public boolean temStatusPainelConselheiro() {
		return this.statusPainel != null;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public Boolean getHouvePausa() {
		return houvePausa;
	}

	public void setHouvePausa(Boolean houvePausa) {
		this.houvePausa = houvePausa;
	}

	public Long getParte() {
		return parte;
	}

	public void setParte(Long parte) {
		this.parte = parte;
	}

	public Arquivo getArquivoNaoAssinado() {
		return arquivoNaoAssinado;
	}

	public void setArquivoNaoAssinado(Arquivo arquivoNaoAssinado) {
		this.arquivoNaoAssinado = arquivoNaoAssinado;
	}

	public Arquivo getArquivoAssinado() {
		return arquivoAssinado;
	}

	public void setArquivoAssinado(Arquivo arquivoAssinado) {
		this.arquivoAssinado = arquivoAssinado;
	}


}


