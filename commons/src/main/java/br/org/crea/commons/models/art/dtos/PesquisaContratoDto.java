package br.org.crea.commons.models.art.dtos;

public class PesquisaContratoDto {
	private int tipoPesquisa;
	
	private Long idContratante;

	public int getTipoPesquisa() {
		return tipoPesquisa;
	}

	public void setTipoPesquisa(int tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}

	public Long getIdContratante() {
		return idContratante;
	}

	public void setIdContratante(Long idContratante) {
		this.idContratante = idContratante;
	}
	
	
}
