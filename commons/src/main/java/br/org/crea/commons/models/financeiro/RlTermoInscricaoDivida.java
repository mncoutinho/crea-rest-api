package br.org.crea.commons.models.financeiro;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="FIN_DIVIDA_TERMOS_INSCRICAO")
public class RlTermoInscricaoDivida implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@OneToOne
	@JoinColumn(name="FK_CODIGO_DIVIDA")
	private FinDivida divida;
	
	@Id
	@OneToOne
	@JoinColumn(name="FK_CODIGO_TERMOS_INSCRICAO")
	private FinTermosInscricao termoInscricao;

	public FinDivida getDivida() {
		return divida;
	}

	public void setDivida(FinDivida divida) {
		this.divida = divida;
	}

	public FinTermosInscricao getTermoInscricao() {
		return termoInscricao;
	}

	public void setTermoInscricao(FinTermosInscricao termoInscricao) {
		this.termoInscricao = termoInscricao;
	}

}
