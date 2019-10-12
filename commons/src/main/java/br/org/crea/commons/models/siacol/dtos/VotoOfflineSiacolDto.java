package br.org.crea.commons.models.siacol.dtos;

import java.util.List;

import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;

public class VotoOfflineSiacolDto {

	private List<ParticipanteReuniaoSiacolDto> listaVotos;
	
	private List<ItemPautaDto> listaItens;
	
	private Long idRlItemPauta;
	
	private ReuniaoSiacolDto reuniao;
	
	private TipoEventoAuditoria eventoAuditoria;
	
	private List<Long> idsPautas;

	public List<ParticipanteReuniaoSiacolDto> getListaVotos() {
		return listaVotos;
	}

	public void setListaVotos(List<ParticipanteReuniaoSiacolDto> listaVotos) {
		this.listaVotos = listaVotos;
	}

	public List<ItemPautaDto> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItemPautaDto> listaItens) {
		this.listaItens = listaItens;
	}

	public Long getIdRlItemPauta() {
		return idRlItemPauta;
	}

	public void setIdRlItemPauta(Long idRlItemPauta) {
		this.idRlItemPauta = idRlItemPauta;
	}

	public ReuniaoSiacolDto getReuniao() {
		return reuniao;
	}

	public void setReuniao(ReuniaoSiacolDto reuniao) {
		this.reuniao = reuniao;
	}

	public TipoEventoAuditoria getEventoAuditoria() {
		return eventoAuditoria;
	}

	public void setEventoAuditoria(TipoEventoAuditoria eventoAuditoria) {
		this.eventoAuditoria = eventoAuditoria;
	}

	public List<Long> getIdsPautas() {
		return idsPautas;
	}

	public void setIdsPautas(List<Long> idsPautas) {
		this.idsPautas = idsPautas;
	}
}
