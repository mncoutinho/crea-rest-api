package br.org.crea.commons.models.siacol.dtos;

import java.util.List;

public class AtualizaPainelPlenariaSiacolDto {
	
	
	private Long idReuniao;
	
	private int quorum;
	
	private Long idRlItemPauta;

	private List<Long> idsPautas;
	
	List<ItemPautaDto> listItens;
	
	private boolean temEnquete;

	public Long getIdReuniao() {
		return idReuniao;
	}

	public void setIdReuniao(Long idReuniao) {
		this.idReuniao = idReuniao;
	}

	public int getQuorum() {
		return quorum;
	}

	public void setQuorum(int quorum) {
		this.quorum = quorum;
	}

	public Long getIdRlItemPauta() {
		return idRlItemPauta;
	}

	public void setIdRlItemPauta(Long idRlItemPauta) {
		this.idRlItemPauta = idRlItemPauta;
	}

	public List<Long> getIdsPautas() {
		return idsPautas;
	}

	public void setIdsPautas(List<Long> idsPautas) {
		this.idsPautas = idsPautas;
	}

	public List<ItemPautaDto> getListItens() {
		return listItens;
	}

	public void setListItens(List<ItemPautaDto> listItens) {
		this.listItens = listItens;
	}

	public boolean isTemEnquete() {
		return temEnquete;
	}

	public void setTemEnquete(boolean temEnquete) {
		this.temEnquete = temEnquete;
	}

}
