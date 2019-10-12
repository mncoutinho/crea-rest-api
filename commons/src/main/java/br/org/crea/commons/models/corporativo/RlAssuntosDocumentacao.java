package br.org.crea.commons.models.corporativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_RL_ASSUNTOS_DOCUMENTACAO")
public class RlAssuntosDocumentacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="FK_ID_PRT_ASSUNTOS")
	private Assunto assunto;
	
	@ManyToOne
	@JoinColumn(name="FK_ID_DOCUMENTACAO")
	private Documentacao documentacao;

	@Column(name="ORIGINAL")
	private Boolean original;
	
	@Column(name="COPIA")
	private Boolean copia;

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	public Documentacao getDocumentacao() {
		return documentacao;
	}

	public void setDocumentacao(Documentacao documentacao) {
		this.documentacao = documentacao;
	}

	public Boolean getOriginal() {
		return original;
	}

	public void setOriginal(Boolean original) {
		this.original = original;
	}

	public Boolean getCopia() {
		return copia;
	}

	public void setCopia(Boolean copia) {
		this.copia = copia;
	}

	
	
	
	
	

}
