package br.org.crea.commons.models.atendimento.dtos;

import java.util.Date;

public class HorariosDisponiveisDto {
	
	private Date horario;
	
	private String dataFormatada;
	
	private String diaFormatado;
	
	private String horaFormatada;
	
	private String turno;
	
	private Long idUnidade;
	
	private Date novaData;

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	public String getDiaFormatado() {
		return diaFormatado;
	}

	public void setDiaFormatado(String diaFormatado) {
		this.diaFormatado = diaFormatado;
	}

	public String getHoraFormatada() {
		return horaFormatada;
	}

	public void setHoraFormatada(String horaFormatada) {
		this.horaFormatada = horaFormatada;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Long getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}

	public Date getNovaData() {
		return novaData;
	}

	public void setNovaData(Date novaData) {
		this.novaData = novaData;
	}

	

}
