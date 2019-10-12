package br.org.crea.commons.docflow.model.response;

import br.org.crea.commons.docflow.model.auth.TokenDocflow;
import br.org.crea.commons.docflow.model.util.MessageDocflow;
import br.org.crea.commons.docflow.model.util.ServiceDocflow;


public class ResponseAuthDocflow {
	
	private TokenDocflow autenticationData;
	
	private ServiceDocflow service;
	
	private MessageDocflow message;


	public TokenDocflow getAutenticationData() {
		return autenticationData;
	}

	public void setAutenticationData(TokenDocflow autenticationData) {
		this.autenticationData = autenticationData;
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
