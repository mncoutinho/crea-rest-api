package br.org.crea.commons.docflow.model.processo;

import java.util.List;

public class ProcessoDocflow {
	
	private List<MetadadoProcessoDocflow> dadosProcesso;
	
	private List<DocumentoProcessoDocflow> docs;

	public List<MetadadoProcessoDocflow> getDadosProcesso() {
		return dadosProcesso;
	}

	public List<DocumentoProcessoDocflow> getDocs() {
		return docs;
	}

	public void setDadosProcesso(List<MetadadoProcessoDocflow> dadosProcesso) {
		this.dadosProcesso = dadosProcesso;
	}

	public void setDocs(List<DocumentoProcessoDocflow> docs) {
		this.docs = docs;
	}
}
