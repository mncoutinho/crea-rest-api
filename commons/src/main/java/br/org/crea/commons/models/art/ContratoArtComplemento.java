package br.org.crea.commons.models.art;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class ContratoArtComplemento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4417657786588097050L;

	@Id
	@OneToOne
	@JoinColumn(name="FK_CONTRATO")
	private ContratoArt contrato;
	
	@Id
	@OneToOne
	@JoinColumn(name="FK_COMPLEMENTO")
	private ComplementoArt complemento;

	public ContratoArt getContrato() {
		return contrato;
	}

	public void setContrato(ContratoArt contrato) {
		this.contrato = contrato;
	}

	public ComplementoArt getComplemento() {
		return complemento;
	}

	public void setComplemento(ComplementoArt complemento) {
		this.complemento = complemento;
	}
}
