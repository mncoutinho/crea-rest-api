package br.org.crea.commons.models.siacol.dtos;

import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;

public class RlAssuntosDto {
	
	private AssuntoDto assunto;
		
	private AssuntoDto assuntoSiacol;
	
	public AssuntoDto getAssunto() {
		return assunto;
	}

	public void setAssunto(AssuntoDto assunto) {
		this.assunto = assunto;
	}

	public AssuntoDto getAssuntoSiacol() {
		return assuntoSiacol;
	}

	public void setAssuntoSiacol(AssuntoDto assuntoSiacol) {
		this.assuntoSiacol = assuntoSiacol;
	}
	
	
}
