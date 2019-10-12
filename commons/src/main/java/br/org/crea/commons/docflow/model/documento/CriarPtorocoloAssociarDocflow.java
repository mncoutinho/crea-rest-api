package br.org.crea.commons.docflow.model.documento;

public class CriarPtorocoloAssociarDocflow {
private String id;
	
	private String protocolo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	@Override
	public String toString() {
		return "Doc [id=" + id + ", protocolo=" + protocolo + "]";
	}
	
}
