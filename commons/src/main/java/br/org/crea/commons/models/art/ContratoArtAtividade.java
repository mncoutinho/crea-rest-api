package br.org.crea.commons.models.art;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ART_CONTRATO_ATIVIDADE")
public class ContratoArtAtividade implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 67412380355179030L;

	@Id
	@OneToOne
	@JoinColumn(name="FK_CONTRATO")
	private ContratoArt contrato;
	
	@Id
	@OneToOne
	@JoinColumn(name="FK_ATIVIDADE")
	private AtividadeTecnicaArt atividade;

	public ContratoArt getContrato() {
		return contrato;
	}

	public void setContrato(ContratoArt contrato) {
		this.contrato = contrato;
	}

	public AtividadeTecnicaArt getAtividade() {
		return atividade;
	}

	public void setAtividade(AtividadeTecnicaArt atividade) {
		this.atividade = atividade;
	}
}
