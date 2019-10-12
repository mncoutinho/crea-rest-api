package br.org.crea.commons.docflow.model.documento;

public class DataCriarProtocoloAssociarDocflow {
	
	private CriarPtorocoloAssociarDocflow proc;

	public CriarPtorocoloAssociarDocflow getDoc() {
		return proc;
	}

	public void setDoc(CriarPtorocoloAssociarDocflow doc) {
		this.proc = doc;
	}

	@Override
	public String toString() {
		return "Data [doc=" + proc + "]";
	}
}
