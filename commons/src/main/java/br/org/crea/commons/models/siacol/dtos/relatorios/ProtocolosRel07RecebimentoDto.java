package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.Date;

public class ProtocolosRel07RecebimentoDto {

	private String idAuditoria;

	private String numeroProtocolo;
	
	private Date data;

	public String getIdAuditoria() {
		return idAuditoria;
	}

	public void setIdAuditoria(String idAuditoria) {
		this.idAuditoria = idAuditoria;
	}

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}
