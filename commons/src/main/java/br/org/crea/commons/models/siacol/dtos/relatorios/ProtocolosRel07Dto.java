package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.Date;

public class ProtocolosRel07Dto {

	private String numeroProtocolo;
	
	private Date dataEntrada;
	
	private Date dataSaida;
	
	private String dataEntradaFormatada;
	
	private String dataSaidaFormatada;
	
	private int diferencaEmDias;

	public int getDiferencaEmDias() {
		return diferencaEmDias;
	}

	public void setDiferencaEmDias(int diferencaEmDias) {
		this.diferencaEmDias = diferencaEmDias;
	}

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getDataEntradaFormatada() {
		return dataEntradaFormatada;
	}

	public void setDataEntradaFormatada(String dataEntradaFormatada) {
		this.dataEntradaFormatada = dataEntradaFormatada;
	}

	public String getDataSaidaFormatada() {
		return dataSaidaFormatada;
	}

	public void setDataSaidaFormatada(String dataSaidaFormatada) {
		this.dataSaidaFormatada = dataSaidaFormatada;
	}
	
}
