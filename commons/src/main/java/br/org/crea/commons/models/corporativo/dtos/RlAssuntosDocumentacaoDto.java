package br.org.crea.commons.models.corporativo.dtos;

import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.Documentacao;

public class RlAssuntosDocumentacaoDto {

	private Assunto assunto;

	private Documentacao documentacao;

	private Boolean original;

	private Boolean copia;

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	public Documentacao getDocumentacao() {
		return documentacao;
	}

	public void setDocumentacao(Documentacao documentacao) {
		this.documentacao = documentacao;
	}

	public Boolean getOriginal() {
		return original;
	}

	public void setOriginal(Boolean original) {
		this.original = original;
	}

	public Boolean getCopia() {
		return copia;
	}

	public void setCopia(Boolean copia) {
		this.copia = copia;
	}
	
	

}
