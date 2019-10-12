package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelSiacol05Dto {
	
	private String status;
	
	private List<RelDepartamentosSiacolDto> departamentos;
	
	private int total;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
