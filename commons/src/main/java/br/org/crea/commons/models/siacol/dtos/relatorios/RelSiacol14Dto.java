package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelSiacol14Dto {
	
	private String mes;
	
	private List<Rel14DepartamentosSiacolDto> departamentos;

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<Rel14DepartamentosSiacolDto> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Rel14DepartamentosSiacolDto> departamentos) {
		this.departamentos = departamentos;
	}
	
}
