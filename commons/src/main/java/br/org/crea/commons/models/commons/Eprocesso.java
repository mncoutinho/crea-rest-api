package br.org.crea.commons.models.commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PRT_E_PROCESSO")
public class Eprocesso {
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="LIBERADO")
	private boolean liberado;
	
	@Column(name="PODE_TRAMITAR")
	private boolean tramitar;
	
	@Column(name="PODE_RECEBER")
	private boolean receber;
	
	@Column(name="PODE_ANEXAR")
	private boolean anexar;
	
	@Column(name="PODE_DESAPENSAR")
	private boolean desapensar;
	
	@Column(name="PODE_DESANEXAR")
	private boolean desanexar;
	
	@Column(name="PODE_APENSAR")
	private boolean apensar;
	
	@Column(name="PODE_SUBSTITUIR")
	private boolean substituir;

	public Long getId() {
		return id;
	}

	public boolean estaLiberado() {
		return liberado;
	}

	public boolean podeTramitar() {
		return tramitar;
	}

	public boolean podeReceber() {
		return receber;
	}

	public boolean podeAnexar() {
		return anexar;
	}

	public boolean podeDesanexar() {
		return desanexar;
	}
	
	public boolean podeDesapensar() {
		return desapensar;
	}

	public boolean podeApensar() {
		return apensar;
	}
	
	public boolean podeSubstituir() {
		return substituir;
	}
	
}
