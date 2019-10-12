package br.org.crea.commons.models.atendimento.dtos;

import java.util.List;

public class CriaHorariosDto {
	
	private List<AgendamentoDto> listAgendamento;
	
	private Long idUnidadeAtendimento;
	
	private String mesAno;

	public List<AgendamentoDto> getListAgendamento() {
		return listAgendamento;
	}

	public void setListAgendamento(List<AgendamentoDto> listAgendamento) {
		this.listAgendamento = listAgendamento;
	}

	public Long getIdUnidadeAtendimento() {
		return idUnidadeAtendimento;
	}

	public void setIdUnidadeAtendimento(Long idUnidadeAtendimento) {
		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}
	

}
