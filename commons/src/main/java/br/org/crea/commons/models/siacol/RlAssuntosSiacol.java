package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.Assunto;

@Entity
@Table(name = "SIACOL_RL_ASSUNTOS")
@SequenceGenerator(name = "sqSiacolRlAssuntos", sequenceName = "SQ_RL_ASSUNTOS", initialValue = 1, allocationSize = 1)
public class RlAssuntosSiacol {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolRlAssuntos")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_PRT_ASSUNTO")
	private Assunto assunto;
	
	@OneToOne
	@JoinColumn(name="FK_ASSUNTO_SIACOL")
	private AssuntoSiacol assuntoSiacol;
	
	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	public AssuntoSiacol getAssuntoSiacol() {
		return assuntoSiacol;
	}

	public void setAssuntoSiacol(AssuntoSiacol assuntoSiacol) {
		this.assuntoSiacol = assuntoSiacol;
	}

	

}
