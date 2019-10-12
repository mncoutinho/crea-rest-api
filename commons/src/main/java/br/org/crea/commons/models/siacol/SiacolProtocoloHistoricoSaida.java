package br.org.crea.commons.models.siacol;

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

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.Evento;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;

@Entity
@Table(name="SIACOL_PROTOCOLO_HIST_TRAMITE")
@SequenceGenerator(name="sqProtHistTramite",sequenceName="SQ_SIACOL_PROT_HIST_TRAMITE",allocationSize = 1)
public class SiacolProtocoloHistoricoSaida {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqProtHistTramite")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_PROTOCOLO")
	private ProtocoloSiacol protocoloSiacol;
	
	@OneToOne
	@JoinColumn(name="FK_ASSUNTO_SIACOL")
	private AssuntoSiacol assuntoSiacol;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private StatusProtocoloSiacol status;
	
	@Column(name="NO_PROTOCOLO")
	private Long numeroProtocolo;
	
	@OneToOne
	@JoinColumn(name="FK_DEPARTAMENTO")
	private Departamento departamento;
	
	@Column(name="PROVISORIO")
	private boolean provisorio;
	
	@Column(name="AD_REFERENDUM")
	private boolean adReferendum;
	
	@OneToOne
	@JoinColumn(name="FK_DECISAO_AD_REFERENDUM")
	private Documento decisaoAdReferendum;
	
	@OneToOne
	@JoinColumn(name="FK_DECISAO")
	private Documento decisao;
	
	@Column(name="FK_ULTIMO_ANALISTA")
	private Long ultimoAnalista;
	
	@Column(name="FK_ULTIMO_CONSELHEIRO")
	private Long ultimoConselheiro;
	
	@OneToOne
	@JoinColumn(name="FK_EVENTO")
	private Evento evento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProtocoloSiacol getProtocoloSiacol() {
		return protocoloSiacol;
	}

	public void setProtocoloSiacol(ProtocoloSiacol protocoloSiacol) {
		this.protocoloSiacol = protocoloSiacol;
	}

	public AssuntoSiacol getAssuntoSiacol() {
		return assuntoSiacol;
	}

	public void setAssuntoSiacol(AssuntoSiacol assuntoSiacol) {
		this.assuntoSiacol = assuntoSiacol;
	}

	public StatusProtocoloSiacol getStatus() {
		return status;
	}

	public void setStatus(StatusProtocoloSiacol status) {
		this.status = status;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public boolean isProvisorio() {
		return provisorio;
	}

	public void setProvisorio(boolean provisorio) {
		this.provisorio = provisorio;
	}

	public boolean isAdReferendum() {
		return adReferendum;
	}

	public void setAdReferendum(boolean adReferendum) {
		this.adReferendum = adReferendum;
	}

	public Documento getDecisaoAdReferendum() {
		return decisaoAdReferendum;
	}

	public void setDecisaoAdReferendum(Documento decisaoAdReferendum) {
		this.decisaoAdReferendum = decisaoAdReferendum;
	}

	public Documento getDecisao() {
		return decisao;
	}

	public void setDecisao(Documento decisao) {
		this.decisao = decisao;
	}

	public Long getUltimoAnalista() {
		return ultimoAnalista;
	}

	public void setUltimoAnalista(Long ultimoAnalista) {
		this.ultimoAnalista = ultimoAnalista;
	}

	public Long getUltimoConselheiro() {
		return ultimoConselheiro;
	}

	public void setUltimoConselheiro(Long ultimoConselheiro) {
		this.ultimoConselheiro = ultimoConselheiro;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public boolean temEvento() {
		return evento != null;
	}

	

}
