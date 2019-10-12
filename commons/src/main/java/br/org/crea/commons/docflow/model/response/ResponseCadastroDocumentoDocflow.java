package br.org.crea.commons.docflow.model.response;

import br.org.crea.commons.docflow.model.documento.DataDocumentoDocflow;
import br.org.crea.commons.docflow.model.util.MessageDocflow;
import br.org.crea.commons.docflow.model.util.ServiceDocflow;


public class ResponseCadastroDocumentoDocflow {
	
	private DataDocumentoDocflow data;
	
	private ServiceDocflow service;
	
	private MessageDocflow message;

	public DataDocumentoDocflow getData() {
		return data;
	}

	public void setData(DataDocumentoDocflow data) {
		this.data = data;
	}

	public ServiceDocflow getService() {
		return service;
	}

	public void setService(ServiceDocflow service) {
		this.service = service;
	}

	public MessageDocflow getMessage() {
		return message;
	}

	public void setMessage(MessageDocflow message) {
		this.message = message;
	}
	
	public boolean hasError(){
		return this.getMessage().getType().equals("error") ? true : false;
	}

	public boolean hasSuccess() {
		return this.getMessage() != null && this.getMessage().getType().equals("success") ? true : false;
	}
	
}
