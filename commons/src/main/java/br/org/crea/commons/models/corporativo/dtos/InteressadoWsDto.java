package br.org.crea.commons.models.corporativo.dtos;

import java.io.Serializable;

import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

public class InteressadoWsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3454090714518984222L;

	private Long id;
	
	private TipoPessoa tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPessoa getTipo() {
		return tipo;
	}

	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo;
	}
	
	
}
