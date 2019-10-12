package br.org.crea.commons.models.siacol;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.commons.EmailEnvio;

@Entity
@Table(name = "SIACOL_RL_REUNIAO_EMAIL")
@SequenceGenerator(name = "sqSiacolRlEmailReuniao", sequenceName = "SQ_RL_EMAIL_REUNIAO", initialValue = 1, allocationSize = 1)
public class RlEmailReuniaoSiacol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqSiacolRlEmailReuniao")
	private Long id;

	@OneToOne
	@JoinColumn(name = "FK_REUNIAO")
	private ReuniaoSiacol reuniao;

	@OneToOne
	@JoinColumn(name = "FK_EMAIL_ENVIO")
	private EmailEnvio emailEnvio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReuniaoSiacol getReuniao() {
		return reuniao;
	}

	public void setReuniao(ReuniaoSiacol reuniao) {
		this.reuniao = reuniao;
	}

	public EmailEnvio getEmailEnvio() {
		return emailEnvio;
	}

	public void setEmailEnvio(EmailEnvio emailEnvio) {
		this.emailEnvio = emailEnvio;
	}

}
