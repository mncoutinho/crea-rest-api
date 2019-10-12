package br.org.crea.commons.models.commons;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "CAD_EMAIL_ENVIO")
@SequenceGenerator(name = "EMAIL_ENVIO_SEQUENCE", sequenceName = "CAD_EMAIL_ENVIO_SEQ", initialValue = 1, allocationSize = 1)
public class EmailEnvio {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMAIL_ENVIO_SEQUENCE")
	@Column(name="ID")
	private Long id;

	@Column(name="ASSUNTO")
	private String assunto;
	
	@Column(name="EMISSOR")
	private String emissor;

	@Lob
	@Column(name="DESTINATARIOS")
	private String destinatarios;
	
	@Lob
	@Column(name="DESTINATARIOSCC")
	private String destinatariosCC;
	
	@Lob
	@Column(name="DESTINATARIOSCCO")
	private String destinatariosCCO;
	
	@Column(name="MENSAGEM")
	private String mensagem;
	
	@Column(name="DT_ULTIMO_ENVIO")
	@Temporal(TemporalType.DATE)
	private Date dataUltimoEnvio;
	
	@Column(name="STATUS")
	private String status;
	
	@OneToOne
	@JoinColumn(name="FK_EVENTO")
	private EventoEmail eventoEmail;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}

	public String getDestinatariosCC() {
		return destinatariosCC;
	}

	public void setDestinatariosCC(String destinatariosCC) {
		this.destinatariosCC = destinatariosCC;
	}

	public String getDestinatariosCCO() {
		return destinatariosCCO;
	}

	public void setDestinatariosCCO(String destinatariosCCO) {
		this.destinatariosCCO = destinatariosCCO;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getDataUltimoEnvio() {
		return dataUltimoEnvio;
	}

	public void setDataUltimoEnvio(Date dataUltimoEnvio) {
		this.dataUltimoEnvio = dataUltimoEnvio;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EventoEmail getEventoEmail() {
		return eventoEmail;
	}

	public void setEventoEmail(EventoEmail eventoEmail) {
		this.eventoEmail = eventoEmail;
	}
}


