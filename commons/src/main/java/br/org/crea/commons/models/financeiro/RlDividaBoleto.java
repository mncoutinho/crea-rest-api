package br.org.crea.commons.models.financeiro;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FIN_DIVIDA_BOLETO")
public class RlDividaBoleto implements Serializable {

	private static final long serialVersionUID = 8895576948157137766L;

	@Id
	@OneToOne
	@JoinColumn(name="FK_CODIGO_BOLETO")
	private Boleto boleto;
	
	@Id
	@OneToOne
	@JoinColumn(name="FK_CODIGO_DIVIDA")
	private FinDivida divida;
		
	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	public FinDivida getDivida() {
		return divida;
	}

	public void setDivida(FinDivida divida) {
		this.divida = divida;
	}
	
}
