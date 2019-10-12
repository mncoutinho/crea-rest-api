package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelSiacol07Dto {
	
	private String assunto;
	
	private List<StatusRel07> status;
	
	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public List<StatusRel07> getStatus() {
		return status;
	}

	public void setStatus(List<StatusRel07> status) {
		this.status = status;
	}

}
