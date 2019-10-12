package br.org.crea.commons.models.financeiro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="FIN_PARCELAMENTO_DIVIDA")
public class FinParcelamentoDivida implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_DIVIDA")
	private FinDivida finDivida;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finDivida == null) ? 0 : finDivida.hashCode());
		result = prime * result + ((numeroTermo == null) ? 0 : numeroTermo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinParcelamentoDivida other = (FinParcelamentoDivida) obj;
		if (finDivida == null) {
			if (other.finDivida != null)
				return false;
		} else if (!finDivida.equals(other.finDivida))
			return false;
		if (numeroTermo == null) {
			if (other.numeroTermo != null)
				return false;
		} else if (!numeroTermo.equals(other.numeroTermo))
			return false;
		return true;
	}

	@Id
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_PARCELAMENTO")
	private Parcelamento parcelamento;

	@Column(name="NUMERO_TERMO")
	private Long numeroTermo;
	

	public FinDivida getFinDivida() {
		return finDivida;
	}

	public void setFinDivida(FinDivida finDivida) {
		this.finDivida = finDivida;
	}

	public Parcelamento getParcelamento() {
		return parcelamento;
	}

	public void setParcelamento(Parcelamento parcelamento) {
		this.parcelamento = parcelamento;
	}

	public Long getNumeroTermo() {
		return numeroTermo;
	}

	public void setNumeroTermo(Long numeroTermo) {
		this.numeroTermo = numeroTermo;
	}

	
	
}
