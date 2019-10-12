package br.org.crea.commons.docflow.model.documento;

public class DataDocumentoDocflow {
	
	private DocumentoDocflow doc;

	public DocumentoDocflow getDoc() {
		return doc;
	}

	public void setDoc(DocumentoDocflow doc) {
		this.doc = doc;
	}

	@Override
	public String toString() {
		return "Data [doc=" + doc + "]";
	}

}
