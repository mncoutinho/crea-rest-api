package br.org.crea.commons.models.commons.dtos;

import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;

public class ProcessoDto {
	
private Long numeroProtocolo;
	
	private Long numeroProcesso;
	
	private AssuntoDto assunto;
	

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Long getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(Long numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public AssuntoDto getAssunto() {
		return assunto;
	}

	public void setAssunto(AssuntoDto assunto) {
		this.assunto = assunto;
	}

}
