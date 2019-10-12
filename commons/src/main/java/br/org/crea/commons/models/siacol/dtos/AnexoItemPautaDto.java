package br.org.crea.commons.models.siacol.dtos;

import br.org.crea.commons.models.commons.dtos.ArquivoDto;

public class AnexoItemPautaDto {
	
	private Long id;
	
	private Long idItemPauta;
	
	private ArquivoDto arquivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdItemPauta() {
		return idItemPauta;
	}

	public void setIdItemPauta(Long idItemPauta) {
		this.idItemPauta = idItemPauta;
	}

	public ArquivoDto getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoDto arquivo) {
		this.arquivo = arquivo;
	}

	
	
	

}
