package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelSiacol03Dto {
	
	private String evento;
	
	private List<RelDepartamentosSiacolDto> departamentos;
	
	private int total;

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public List<RelDepartamentosSiacolDto> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<RelDepartamentosSiacolDto> departamentos) {
		this.departamentos = departamentos;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	

}
