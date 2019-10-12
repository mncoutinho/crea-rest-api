package br.org.crea.commons.models.art;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class ContratoArtEspecificacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8442687141894608762L;

	@Id
	@OneToOne
	@JoinColumn(name="FK_CONTRATO")
	private ContratoArt contrato;
	
	@Id
	@OneToOne
	@JoinColumn(name="FK_ESPECIFICACAO")
	private EspecificacaoAtividadeArt especificacao;

	public ContratoArt getContrato() {
		return contrato;
	}

	public void setContrato(ContratoArt contrato) {
		this.contrato = contrato;
	}

	public EspecificacaoAtividadeArt getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(EspecificacaoAtividadeArt especificacao) {
		this.especificacao = especificacao;
	}
}
