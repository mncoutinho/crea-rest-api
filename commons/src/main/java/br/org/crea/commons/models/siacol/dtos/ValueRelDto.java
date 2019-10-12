package br.org.crea.commons.models.siacol.dtos;

import java.util.List;

import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol01Dto;

public class ValueRelDto {
	
	private String value;
	
	private List<RelDetalhadoSiacol01Dto> protocolos;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<RelDetalhadoSiacol01Dto> getProtocolos() {
		return protocolos;
	}

	public void setProtocolos(List<RelDetalhadoSiacol01Dto> protocolos) {
		this.protocolos = protocolos;
	}

}
