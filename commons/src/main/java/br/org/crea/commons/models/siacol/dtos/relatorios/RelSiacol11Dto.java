package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.util.DateUtils;

public class RelSiacol11Dto {
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private String dataFormatada;
	
	private String numeroProtocolo;
	
	private String textoAuditoria;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public String getTextoAuditoria() {
		return textoAuditoria;
	}

	public void setTextoAuditoria(String textoAuditoria) {
		this.textoAuditoria = textoAuditoria;
	}

	public String getDataFormatada() {
		return DateUtils.format(this.getData(), DateUtils.DD_MM_YYYY_HH_MM);
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
	
	

}
