package br.org.crea.commons.models.cadastro.dtos.empresa;

import java.io.Serializable;

public class TituloEmpresaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigoTituloCrea;
	
	private String tituloCrea;
	
	private Long codigoTituloConfea;
	
	private String tituloConfea;

	public Long getCodigoTituloCrea() {
		return codigoTituloCrea;
	}

	public void setCodigoTituloCrea(Long codigoTituloCrea) {
		this.codigoTituloCrea = codigoTituloCrea;
	}

	public String getTituloConfea() {
		return tituloConfea;
	}

	public void setTituloConfea(String tituloConfea) {
		this.tituloConfea = tituloConfea;
	}

	public Long getCodigoTituloConfea() {
		return codigoTituloConfea;
	}

	public void setCodigoTituloConfea(Long codigoTituloConfea) {
		this.codigoTituloConfea = codigoTituloConfea;
	}

	public String getTituloCrea() {
		return tituloCrea;
	}

	public void setTituloCrea(String tituloCrea) {
		this.tituloCrea = tituloCrea;
	}
	
}

