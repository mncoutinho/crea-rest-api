package br.org.crea.commons.docflow.model.processo;

import java.util.List;

public class DocumentoProcessoDocflow {
	
	private List<MetadadoProcessoDocflow> dadosDocumento;

	public List<MetadadoProcessoDocflow> getDadosDocumento() {
		return dadosDocumento;
	}

	public void setDadosDocumento(List<MetadadoProcessoDocflow> dadosDocumento) {
		this.dadosDocumento = dadosDocumento;
	}

}
