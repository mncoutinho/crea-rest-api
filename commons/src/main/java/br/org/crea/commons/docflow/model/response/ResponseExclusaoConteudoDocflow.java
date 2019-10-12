package br.org.crea.commons.docflow.model.response;

import br.org.crea.commons.docflow.model.documento.DataExclusaoConteudoDocflow;
import br.org.crea.commons.docflow.model.util.MessageDocflow;
import br.org.crea.commons.docflow.model.util.ServiceDocflow;

public class ResponseExclusaoConteudoDocflow {

	private DataExclusaoConteudoDocflow data;
	
	private ServiceDocflow service;
	
	private MessageDocflow message;

	public DataExclusaoConteudoDocflow getData() {
		return data;
	}

	public void setData(DataExclusaoConteudoDocflow data) {
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
}
