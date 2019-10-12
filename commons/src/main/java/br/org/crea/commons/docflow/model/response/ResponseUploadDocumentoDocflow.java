package br.org.crea.commons.docflow.model.response;

import br.org.crea.commons.docflow.model.util.MessageDocflow;
import br.org.crea.commons.docflow.model.util.ServiceDocflow;

public class ResponseUploadDocumentoDocflow {
	
	private String data;
	
	private ServiceDocflow service;
	
	private MessageDocflow message;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public void setData(Object object) {
		this.data = object.toString();
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
	
	

}
